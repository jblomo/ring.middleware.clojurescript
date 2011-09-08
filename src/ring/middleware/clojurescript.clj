(ns ring.middleware.clojurescript
  "ClojureScript compiling."
  (:require [cljs.closure :as cljsc])
  (:import java.io.File))

(defn- path-str
  ([basename] (str (File. basename)))
  ([dirname basename] (str (File. dirname basename))))

(defn- cljsc-output-options [dest-root dest-file]
  {:output-dir (path-str dest-root)
   :output-to (path-str dest-root dest-file)})

(defn wrap-clojurescript
  "Wrap an app such that out-of-date ClojureScript in src-root will be compiled
   and placed into dest-root. Typically this is used to wrap routes that serve
   the compiled javascript."
  [app src-root dest-root dest-file & [opts]]
  (let [opts (merge {:cljs-path (path-str src-root)
                     :cljsc-options (cljsc-output-options dest-root dest-file)}
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
