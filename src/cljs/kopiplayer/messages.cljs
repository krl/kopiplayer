(ns kopiplayer.messages
  (:use     [cljs.reader :only [read-string]]))

;; messages

(def handlers* (atom {}))

(defn define-handler 
  "Setter function to get around namespace weirdness"
  [name function]
  (swap! handlers* #(assoc % name function)))

(defn handle-message [msg]
  (js/console.log "handle message")
  (js/console.log @handlers*)
  (if-let [handler-function (get @handlers* (first msg))]
    (handler-function (second msg))
    (js/console.log "WARNING: unknown message" (first msg))))

;; websocket stuff

(def websocket 
  (js/WebSocket. "ws://localhost:8080/socket"))

(defn message [type & [data]]
  (js/console.log (str "message " type data))
  (.send websocket [type data]))

(defn on-message [message]
  (js/console.log "got message")
  (js/console.log (.-data message))
  (handle-message (read-string (.-data message))))

(set! (.-onopen websocket) #(js/console.log "connected!"))
(set! (.-onmessage websocket) on-message)
