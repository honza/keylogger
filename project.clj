(defproject keylogger "0.1"
  :description "keylogger analyzer"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot keylogger.core
  :target-path "target/%s"
  :license {:name "GPLv3"}
  :profiles {:uberjar {:aot :all}})
