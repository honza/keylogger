(defproject keylogger "0.1.0"
  :description "keylogger analyzer"
  :url "https://github.com/honza/keylogger"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot keylogger.core
  :target-path "target/%s"
  :license {:name "GPLv3"}
  :profiles {:uberjar {:aot :all}})
