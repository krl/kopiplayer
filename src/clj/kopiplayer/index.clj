(ns kopiplayer.index
  (:use [kopiplayer.debug])
  (:require [clucy.core :as clucy]))

(def index (clucy/disk-index "index.db"))

(defn add-or-update-document [index document]
  (clucy/search-and-delete index (str "id:" (:id document)))
  (clucy/add index document))

(defn index-documents [documents]
  (map (partial add-or-update-document index) documents))

(defn search [& terms]
  (clucy/search index (apply str terms) 99999))

(defn get-id [id]
  (first
   (search "id:" id)))

(defn artist-info
  "Gets the releases and recordings from this artist"
  [id]
  (let [releases        (search "type:release AND artist-id:" id)
        with-recordings (map (fn [release]
                               (assoc release :recordings
                                      (sort-by #(Integer. (:number %))
                                               (search "type:recording AND "
                                                       "release-id:" (:id release)))))
                             releases)
        sorted           (sort-by :date with-recordings)]
    (assoc (get-id id)
      :releases sorted)))

(defn all-artists []
  (sort-by first
           (seq
            (reduce (fn [artists artist]
                      (let [key (-> artist :sort-name first str keyword)]              
                        (assoc artists key 
                               (sort-by :sort-name
                                        (conj (or (key artists) '())
                                              artist)))))
                    {}
                    (search "type:artist")))))
