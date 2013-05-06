(ns kopiplayer.play
  (:use [jayq.core :only [$ attr]]
        [kopiplayer.messages :only [message]])
  (:use-macros [kopiplayer.macros :only [defevent defhandler]])
  (:require [kopiplayer.templates :as tpl]))

(def $audio ($ :#audio))

;; handlers

(defhandler play [id]
  (attr $audio :src (str "/play/" id))
  (.play (first $audio)))

;; events

(defevent tpl/recording :click [event]
  (message :play (-> event :value :id)))