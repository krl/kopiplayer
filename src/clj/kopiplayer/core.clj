(ns kopiplayer.core
  (:use [kopiplayer.debug]
        [kopiplayer.musicbrainz]
        [kopiplayer.parse]
        [kopiplayer.index]
        [clojure.pprint]))
        
(doall
 (map #(index-documents (identify-release (files-in-folder %)))
      (subdirectories "/home/krl/testmusik/")))