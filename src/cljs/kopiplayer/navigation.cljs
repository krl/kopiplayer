(ns kopiplayer.navigation
  (:use [jayq.core :only [$ append]]
        [kopiplayer.messages :only [message]])
  (:use-macros [kopiplayer.macros :only [defevent]])
  (:require [kopiplayer.templates :as tpl]))

(def $navigation ($ :#navigation))

(defn setup-navigation []
  (doall
   (map #(append $navigation (tpl/menu-button %))
        [{:caption "settings"
          :message :display-settings}
         {:caption "library"
          :message :display-library}
         {:caption "playlist"
          :message :display-playlist}])))

;; init
(setup-navigation)

(defevent tpl/menu-button :click [event]
  (message (-> event :value :message)))
