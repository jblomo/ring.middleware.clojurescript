(ns ring.middleware.clojurescript
  "ClojureScript compiling."
  (:require [cljs.closure :as cljsc])
  (:use [clojure.string :only (join)])
  (:import [java.io File]))

(defn- file-name
  "Given one or more directory names, join them together to form a system compatible path"
  [& paths]
  (join File/separatorChar paths))

(defn- default-paths
  "Return a cljsc/build compatible map of options given the root path of a
  clojurescript project."
  [root-path]
  {:source-dir (file-name root-path "src")
   :output-dir (file-name root-path "out")
   :output-to (file-name root-path "bootstrap.js")})

(defn wrap-clojurescript
  "Wrap an app such that out-of-date ClojureScript will be compiled.
  
  Use opts to override any of the default below and supply additional options to
  cljs/build.  By default, no Closure optimizations are used.

  :source-dir root-path/src
  :output-dir root-path/out
  :output-to  root-path/bootstrap.js"
  [app ^String root-path & [opts]]
  (let [opts (merge (default-paths root-path) opts)]
    (fn [req]
      (let [src-paths (file-seq (File. (:source-dir opts)))
            out-dir (File. (:output-dir opts))
            out-to (File. (:output-to opts))
            out-paths (cons out-to (file-seq out-dir))]

        (when (or (some #(not (.exists %)) [out-to out-dir])
                  (> (apply max (map #(.lastModified %) src-paths))
                     (apply min (map #(.lastModified %) out-paths))))
          ;if the newest src is newer than the oldest output, then recompile
          (cljsc/build (:source-dir opts) opts))

        (app req)))))

(defn -main
  "Convinience function for compiling ClojureScript on the command line.

  This function logically takes the same arguements as wrap-clojurescript, but
  without `app` and all options are considered strings from the command line.

  lein run -m ring.middleware.clojurescript 'cljs' '{:optimizations :advanced}'"
  [^String root-path & [string-opts]]
  (let [opts (load-string string-opts)
        fake-app (constantly "Complete")
        compiler (wrap-clojurescript fake-app root-path opts)]
    (compiler {})))
    
