(defproject ring.middleware.clojurescript "0.5.0"
  :description "Ring middleware that compiles ClojureScript and serves javascript"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/clojurescript "0.0-971"]]
  :dev-dependencies [[ring/ring-core "1.0.2" :exclusions [org.clojure/clojure org.clojure/clojure-contrib]]])
