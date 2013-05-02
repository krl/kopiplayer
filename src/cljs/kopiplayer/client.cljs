(ns kopiplayer.client
  (:require [crate.core :as crate]
            [kopiplayer.templates :as tpl])
  (:use-macros [kopiplayer.macros :only [defevent]])
  (:use [jayq.core :only [$ css append inner replace-with
                          delegate data attr]]
        [cljs.reader :only [read-string]]))

(def $body ($ :body))
(def $audio ($ :#audio))
(def $artists ($ :#artists))
(def $artist-info ($ :#artist_info))
(def $playing ($ :#playing))

(def websocket (js/WebSocket. "ws://localhost:8080/socket"))

(defn message [& stuff]
  (js/console.log (str "message " stuff))
  (.send websocket (js->clj (vec stuff))))

(defn display-artists [artists]
  (doseq [[letter artistlist] artists]
    (let [box ($ (tpl/letter-box (name letter)))]

      (doseq [artist artistlist]
        (append box (tpl/artist-entry artist)))

      (append $artists box))))

(defn display-artist-info [artist]  
  (append (inner $artist-info "") (tpl/artist-info artist)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; events
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defevent tpl/recording :click
  #(message :play-recording (-> % :value :id)))

(defevent tpl/artist-entry :click
  #(message :artist-info (-> % :value :id)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; playing
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn play-recording [id]
  (attr $audio :src (str "/play/" id))
  (.play (first $audio)))

(defn update-status [status])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; message handling
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn handle-message [[command & args]]
  (js/console.log (print-str "handle-message" command))
  (case command
    :artist-info (display-artist-info (first args))
    :artists     (display-artists (first args))
    :status      (update-status (first args))
    :play        (play-recording (first args))))

(set! (.-onopen websocket) #(js/console.log "connected!"))
(set! (.-onmessage websocket) #(handle-message (read-string (.-data %))))