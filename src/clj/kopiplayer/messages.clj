(ns kopiplayer.messages
  (:use [kopiplayer.debug])
  (:require [lamina.core :as lamina]))

(def handlers* (atom {}))

(defn define-handler 
  "Setter function to get around namespace weirdness"
  [name function]
  (swap! handlers* #(assoc % name function)))

(defn message [channel msg & [data]]
  (lamina/enqueue channel (pr-str [msg data])))

(defn handle-message [state msg]
  (println "handle message" msg)
  (if-let [handler-function (get @handlers* (dbg (first msg)))]
    (handler-function state (second msg))
    (do
      (println "WARNING: unknown message")
      state)))

;; macros

(defmacro defhandler [name bindings body]
  (assert (= (type name) clojure.lang.Symbol) "Name must be symbol")
  (assert (= 2 (count bindings)) "Handler must take two arguments [state msg]")
  `(define-handler
     ~(keyword name)
     (fn ~bindings (do ~body 
                       ;; return same state
                       ~(first bindings)))))

(defmacro defhandler-state [name bindings body]
  (assert (= (type name) clojure.lang.Symbol) "Name must be symbol")
  (assert (= 2 (count bindings)) "Handler must take two arguments [state msg]")
  `(define-handler
     ~(keyword name)
     (fn ~bindings ~@body)))