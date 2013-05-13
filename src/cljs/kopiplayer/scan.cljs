(ns kopiplayer.scan
  (:use [jayq.core :only [$ append inner]]
        [kopiplayer.messages :only [message]])
  (:require [crate.core :as crate])
  (:use-macros [kopiplayer.macros :only [defhandler defevent]]
               [crate.def-macros :only [defpartial]]))

(def $sidebar ($ :#sidebar))
(def $content ($ :#content))

;; templates

(defpartial scan-view []
  [:form {:action "POST"}
   [:input {:value "/home/krl/nicotine/"}]
   [:input {:type "submit"}]])

;; handlers

(defhandler scan-view [msg]
  (inner $sidebar "")
  (inner $content (scan-view)))