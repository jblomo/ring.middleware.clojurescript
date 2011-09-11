# ring.middleware.clojurescript

BETA

Wraps requests such that ClojureScript is conditionally (re)compiled before being served.

## Usage

If you have ClojureScript source code in `cljs/src/` and your HTML is including `cljs/bootstrap.js`

    (use 'ring.middleware.clojurescript)
    (defn app [req] ...)
	(def cljsc-app (wrap-clojurescript app "cljs"))

Optionally, you can use the options dictionary to override those path defaults,
as well as provide extra arguements to the ClojureScript compiler:

    (wrap-clojurescript app "cljs" {:output-to "resources/js/myapp.js"
                                    :optimizations :advanced})

## Command line

You may also use the command line version, which accepts the same style of
arguements:

     lein -m ring.middleware.clojurescript 'cljs' '{:optimizations :advanced}'

## License

Copyright (C) 2011 Jim Blomo

Distributed under the Eclipse Public License, the same as Clojure.
