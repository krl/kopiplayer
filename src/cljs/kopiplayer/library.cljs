(ns kopiplayer.library
  (:use [jayq.core :only [$ append inner]]
        [kopiplayer.messages :only [message]]
        [kopiplayer.play :only [play-song]])
  (:use-macros [kopiplayer.macros :only [defhandler defevent]])
  (:require [kopiplayer.templates :as tpl]))

(def $sidebar ($ :#sidebar))
(def $content ($ :#content))

(defn display-artists [artists]
  (let [container ($ (tpl/div))]
    (doseq [[letter artistlist] artists]
      (let [letterbox ($ (tpl/letter-box (name letter)))]

        (doseq [artist artistlist]
          (append letterbox (tpl/artist-entry artist)))

        (append container letterbox)))
    (append $sidebar container)))

(defn display-artist-info [artist]
  (inner $content (tpl/artist-info artist)))

;; handlers
  
(defhandler display-library [msg]
  (js/console.log msg)
  (display-artists msg))

(defhandler display-artist-info [msg]
  (display-artist-info msg))

;; events

(defevent tpl/artist-entry :click [event]
   (message :display-artist-info (-> event :value :id)))