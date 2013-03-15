(defproject ring.middleware.clojurescript "0.5.4"
  :description "Ring middleware that compiles ClojureScript and serves javascript"
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.clojure/clojurescript "0.0-1586"]]
  :profiles {:dev {:dependencies [[ring/ring-core "1.1.8"]]}}
  :min-lein-version "2.0.0")
