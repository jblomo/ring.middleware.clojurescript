(defproject ring.middleware.clojurescript "0.3.0-SNAPSHOT"
  :description "Ring middleware that compiles ClojureScript and serves javascript"
  :dependencies [[org.clojure/clojure "1.3.0-beta3"]
                 [cljs-compiler-jar "0.1.0-SNAPSHOT"]
                 [goog-jar "1.0.0"]]
  :dev-dependencies [[lein-clojars "0.7.0"]
                     [ring/ring-core "0.3.11" :exclusions [org.clojure/clojure org.clojure/clojure-contrib]]])
