(ns kopiplayer.macros)

(defmacro defevent [template event event-fn]
  `(jayq.core/delegate 
    (jayq.core/$ :body) ~template ~event
    (fn [e#]
      (.preventDefault e#)
      (let [element# (jayq.core/$ (.-currentTarget e#))]
        (~event-fn {:value (cljs.reader/read-string 
                            (jayq.core/data element# :value))
                    :element element#
                    :event   e#})))))
