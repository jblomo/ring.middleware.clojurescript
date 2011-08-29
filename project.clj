(defproject ring.middleware.clojurescript "0.0.3-SNAPSHOT"
  :description "ring middleware that wraps compiles clojurescript and serves javascript"
  :dependencies [[org.clojure/clojure "1.3.0-beta2"]
                 [cljs-compiler-jar "0.1.0-SNAPSHOT"]
                 [goog-jar "1.0.0"]]
  :dev-dependencies [[compojure "0.6.5" :exclusions [org.clojure/clojure org.clojure/clojure-contrib]]])
