goog.provide('hello.core');
goog.require('cljs.core');
goog.require('hello.foo.bar');
hello.core.greet = (function greet(n){
return cljs.core.str.call(null,"Hello ",n);
});
goog.exportSymbol('greet', hello.core.greet);
hello.core.sum = (function sum(xs){
return hello.foo.bar.sum.call(null,xs);
});
goog.exportSymbol('hello.core.sum', hello.core.sum);
