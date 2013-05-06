(ns kopiplayer.play
  (:use [kopiplayer.messages :only [defhandler message]])
  (:require [kopiplayer.index :as idx]))

(defhandler play [state id]
  (let [recording (idx/get-id id)
        file      (first (idx/search "recording-id:" (:id recording)))]
    (message (:channel state) :play (:id file))))