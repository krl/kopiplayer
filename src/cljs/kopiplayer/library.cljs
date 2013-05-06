(ns kopiplayer.library
  (:use [jayq.core :only [$ append inner]]
        [kopiplayer.messages :only [message]]
        [kopiplayer.play :only [play-song]])
  (:use-macros [kopiplayer.macros :only [defhandler defevent]])
  (:require [kopiplayer.templates :as tpl]))

(def $artists ($ :#artists))
(def $artist-info ($ :#artist_info))

(defn display-artists [artists]
  (doseq [[letter artistlist] artists]
    (let [box ($ (tpl/letter-box (name letter)))]

      (doseq [artist artistlist]
        (append box (tpl/artist-entry artist)))

      (append $artists box))))

(defn display-artist-info [artist]
  (append (inner $artist-info "") (tpl/artist-info artist)))

;; handlers
  
(defhandler display-library [msg]
  (js/console.log msg)
  (display-artists msg))

(defhandler display-artist-info [msg]
  (display-artist-info msg))

;; events

(defevent tpl/artist-entry :click [event]
   (message :display-artist-info (-> event :value :id)))