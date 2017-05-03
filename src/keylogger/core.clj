;; keylogger analyzer --- analyze your typing habits
;; Copyright (C) 2017  Honza Pokorny <me@honza.ca>
;;
;; This program is free software: you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;; GNU General Public License for more details.
;;
;; You should have received a copy of the GNU General Public License
;; along with this program.  If not, see <http://www.gnu.org/licenses/>.

(ns keylogger.core
  (:gen-class)
  (:require [clojure.string :as s]))

(def keyboards
  {:qwerty {:home (set "asdfghjkl;")}
   :dvorak {:home (set "aoeuidhtns")}
   :dvorak-programmer {:home (set "aoeuidhtns")}
   :colemak {:home (set "arstdhneio")}})

(defn str-take [n s]
  (subs s 0 n))

(defn symbol-on-home-row? [keyboard char]
  (contains?
   (get-in keyboards [keyboard :home] #{})
   char))

(defn process-line [line]
  (let [date (str-take 26 line)
        [d t _ & r] (s/split line #" ")]
    {:date d
     :time t
     :string (s/join " " r)}))

(defn line->special [line]
  (->> (:string line)
       (re-seq #"<([a-zA-Z0-9\-#\+]+)>")
       (map second)))

(defn clean? [line]
  (cond
    (zero? (count line)) false
    ;; poor man's "starts with a number" lol
    (not (s/starts-with? line "2")) false
    :else true))

(defn get-special-chars [processed-lines]
  (->> processed-lines
       (mapcat line->special)
       set))

(defn get-big-string [processed-lines]
  (->> processed-lines
       (map :string)
       (s/join "")))

(defn remove-special-from-big-string [big-string special]
  (reduce #(s/replace %1 (str "<" %2 ">") "") big-string special))

(defn process [filename]
  (let [log (slurp filename)
        lines (s/split-lines log)
        clean-lines (filter clean? lines)
        processed-lines (map process-line clean-lines)
        special (get-special-chars processed-lines)
        big-str (get-big-string processed-lines)]
    (remove-special-from-big-string big-str special)))

(defn ->percentage [total [letter number]]
  [letter (/ number (/ total 100))])

(defn analyze [clean-big-string]
  (let [total (count clean-big-string)]
    (->> (frequencies clean-big-string)
         (map (partial ->percentage total))
         (into {}))))

(defn percentage-on-home-row [data keyboard]
  (let [f (fn [acc [letter number]]
            (if (symbol-on-home-row? keyboard letter)
              (+ acc number)
              acc))]
    (float (reduce f 0 data))))

(defn percentage-on-home-row-all [data]
  (let [f (partial percentage-on-home-row data)]
    (->> (keys keyboards)
         (map #(hash-map % (f %)))
         (into {}))))

(defn rank-by-home-row [data]
  (->> data
       percentage-on-home-row-all
       (sort-by second)
       reverse))

(defn pretty-format-rank [rank]
  (s/join "\n"
          (map (fn [[k v]]
                 (str (name k) ": " v "%"))
               rank)))

(defn -main [& args]
  (when args
    (->> args
         first
         process
         ;; analyze
         ;; rank-by-home-row
         ;; pretty-format-rank
         println)))
