(ns ring.middleware.clojurescript.test
  (:use ring.middleware.clojurescript
        clojure.test)
  (:require [ring.middleware.file :as rfile]
            [clojure.java.io :as io])
  (:import [java.io File]))

(def bootstrap-js (File. "cljs/bootstrap.js"))
(def base-js (File. "cljs/out/goog/base.js"))

(def app (-> (constantly :response)
           (rfile/wrap-file "cljs")
           (wrap-clojurescript "cljs")))

(deftest test-bootstrap-generated
  (let [{:keys [status headers body]}
         (app {:request-method :get :uri "/bootstrap.js" :headers {}})]
    (is (= 200 status))
    (is (= {} headers))
    (is (= bootstrap-js body))))

(deftest test-goog-generated
  (let [{:keys [status headers body]}
         (app {:request-method :get :uri "/out/goog/base.js" :headers {}})]
    (is (= 200 status))
    (is (= {} headers))
    (is (= base-js body))))

(deftest test-recompile
  (let [pre-timestamp (.lastModified bootstrap-js)
        new-file (File. "cljs/src/new-file.cljs")]
    (with-open [fh (io/writer new-file)]
      (.write fh "(ns test-ns)"))
    (Thread/sleep 1000) ; filesystem only has second resolution
    (let [{:keys [body]}
          (app {:request-method :get :uri "/bootstrap.js" :headers {}})]
      (is (< pre-timestamp (.lastModified body))))))

