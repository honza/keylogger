(defproject keylogger "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.7.1"]]
  :main ^:skip-aot keylogger.core
  :target-path "target/%s"
  :license {:name "GPLv3"}
  :profiles {:uberjar {:aot :all}})
