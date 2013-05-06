(ns kopiplayer.macros)

(defmacro defevent [template event bindings & body]  
  (assert (= 1 (count bindings)) "Event must take one argument")  
  `(jayq.core/delegate 
    (jayq.core/$ :body) ~template ~event
    (fn [e#]
      (.preventDefault e#)
      (let [element# (jayq.core/$ (.-currentTarget e#))
            value#    (cljs.reader/read-string 
                      (jayq.core/data element# :value))]
        (when value#
          ((fn ~bindings ~@body)
           {:value value#
            :element element#
            :event   e#}))))))

(defmacro defhandler [name bindings & body]
  (assert (= (type name) clojure.lang.Symbol) "Name must be symbol")
  (assert (= 1 (count bindings)) "Handler must take one argument")
  ;; timeout is for allowing the whole namespace to load
  ;; to make sure define-handler is callable
  `(js/setTimeout
    #(kopiplayer.messages/define-handler
       ~(keyword name)
       (fn ~bindings ~@body))
    1))