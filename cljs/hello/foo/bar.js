goog.provide('hello.foo.bar');
goog.require('cljs.core');
hello.foo.bar.sum = (function sum(xs){
return cljs.core.reduce.call(null,cljs.core._PLUS_,0,xs);
});
