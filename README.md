# ring.middleware.clojurescript

ALPHA

Wraps requests such that ClojureScript is conditionally (re)compiled before being served.

## Usage

If you have ClojureScript source code in `cljs/src/` and your HTML is including `cljs/bootstrap.js`

    (use 'ring.middleware.clojurescript)
    (defn app [req] ...)
	(def cljsc-app (wrap-clojurscript app "cljs"))


## License

Copyright (C) 2011 Jim Blomo

Distributed under the Eclipse Public License, the same as Clojure.
