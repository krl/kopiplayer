(ns kopiplayer
  (:require [crate.core :as crate])
  (:use-macros [crate.def-macros :only [defpartial]])
  (:use [jayq.core :only [$ css append inner replace-with
                          delegate data attr]]
        [cljs.reader :only [read-string]]))

(def $body ($ :body))
(def $audio ($ :#audio))
(def $artists ($ :#artists))
(def $artist-info ($ :#artist_info))

(def websocket (js/WebSocket. "ws://localhost:8080/socket"))

(defn message [& stuff]
  (js/console.log (str "message " stuff))
  (.send websocket (js->clj (vec stuff))))

(defpartial letter-box-tpl [letter]
  [:div.letterbox
   [:div.letter_separator
    [:h1.letter letter]]])

(defpartial artist-entry-tpl [artist]
  [:h2 {:data-value artist} (:name artist)])

(defpartial recording-tpl [recording]
  [:tr {:data-value recording}
   [:td [:b (:number recording)]]
   [:td (:title  recording)]
   [:td (:length recording)]])

(delegate $body recording-tpl :click
          (fn [e]
            (.preventDefault e)
            (this-as
             me (let [value (read-string (data ($ me) :value))]
                  (message :play-recording (:id value))))))

(delegate $body artist-entry-tpl :click
          (fn [e]
            (.preventDefault e)
            (this-as
             me (let [value (read-string (data ($ me) :value))]
                  (message :artist-info (:id value))))))
          
(defpartial artist-info-tpl [artist]
  [:div
   [:h1 (:name artist)]
   (for [release (:releases artist)]
     [:div
      [:h2 (:title release)
       [:p.date (:date release)]]
      [:table
       (map recording-tpl (:recordings release))]])])

(defn display-artists [artists]
  (doseq [[letter artistlist] artists]
    (let [box ($ (letter-box-tpl (name letter)))]

      (doseq [artist artistlist]
        (append box (artist-entry-tpl artist)))

      (append $artists box))))

(defn display-artist-info [artist]  
  (append (inner $artist-info "") (artist-info-tpl artist)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; playing
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn play-recording [id]
  (attr $audio :src (str "/play/" id))
  (.play (first $audio)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; message handling
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(defn handle-message [[command & args]]
  (js/console.log (print-str "handle-message" command))
  (case command
    :artist-info (display-artist-info (first args))
    :artists     (display-artists (first args))
    :play        (play-recording (first args))))

(set! (.-onopen websocket) #(js/console.log "connected!"))
(set! (.-onmessage websocket) #(handle-message (read-string (.-data %))))