(ns kopiplayer.templates
  (:require [crate.core :as crate])
  (:use-macros [crate.def-macros :only [defpartial]]))

(defpartial div []
  [:div])

(defpartial letter-box [letter]
  [:div.letterbox
   [:div.letter_separator
    [:h1.letter letter]]])

(defpartial artist-entry [artist]
  [:h2 {:data-value artist} (:name artist)])

(defpartial recording [recording]
  [:tr {:data-value recording}
   [:td [:b (:number recording)]]
   [:td (:title  recording)]])

(defpartial artist-info [artist]
  [:div
   [:h1 (:name artist)]
   (for [release (:releases artist)]
     [:div
      [:h2
       [:span (:title release)]
       [:span.date (str " (" (:date release) ")")]]
      [:table
       (map recording (:recordings release))]])])

(defpartial menu-button [data]
  [:div.button {:data-value data}
   (:caption data)])