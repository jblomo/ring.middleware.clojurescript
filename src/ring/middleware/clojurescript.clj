(ns ring.middleware.clojurescript
  "ClojureScript compiling."
  (:require [cljs.closure :as cljsc])
  (:import java.io.File))

(defn wrap-clojurescript
  "Wrap an app such that out-of-date ClojureScript will be compiled and placed
  into the root-path.  Typically this is used to wrap routes that serve the
  compiled javascript."
  [app ^String root-path & [opts]]
  (let [opts (merge
               {:cljs-path (str (File. root-path "src"))
                :cljsc-options {;:optimizations :simple
                                :output-dir (str (File. root-path "out"))
                                :output-to (str (File. root-path "bootstrap.js"))}}
               opts)]
    (fn [req]
      (let [src-paths (file-seq (File. (:cljs-path opts)))
            out-dir (File. (get-in opts [:cljsc-options :output-dir]))
            out-to (File. (get-in opts [:cljsc-options :output-to]))
            out-paths (cons out-to (file-seq out-dir))]

        (when (or (some #(not (.exists %)) [out-to out-dir])
                  (> (apply max (map #(.lastModified %) src-paths))
                     (apply min (map #(.lastModified %) out-paths))))
          ;if the newest src is newer than the oldest output, then recompile
          (cljsc/build (:cljs-path opts) (:cljsc-options opts)))

        (app req)))))
