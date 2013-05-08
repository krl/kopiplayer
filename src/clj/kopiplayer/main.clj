(ns  kopiplayer.main
  (:use [kopiplayer.debug]
        [kopiplayer.musicbrainz]
        [kopiplayer.parse]
        [kopiplayer.index]
        [clojure.pprint])
  (:require 
    [kopiplayer.server :as server]))

(defn -main [& m]
  (doall
   (map #(index-documents (identify-release (files-in-folder %)))
        (subdirectories "/Users/karl/desktop/grejer/")))
  (server/start-server))
