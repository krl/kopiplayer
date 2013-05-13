(ns kopiplayer.discogs
  (:use [clojure.pprint]
        [clojure.set :only [union]]
        [kopiplayer.debug])
  (:require [kopiplayer.json :as json]))

(defn discogs-request [& parts]
  (json/request (str "http://api.discogs.com/"
                     (apply str parts))))

(defn find-releases [artist release]
  (discogs-request "search"
                   "?type=releases"
                   "&q=artist:\"" artist "\""
                   " AND "
                   "title:\"" release "\""))

(defn release-id-from-uri [uri]
  (last (re-seq #"[0-9]+" uri)))

(defn get-release-data [id]
  (-> (discogs-request "release/" id)
      :resp :release))

(defn pick-release [result files]
  (get-release-data
   (release-id-from-uri (:uri (first result)))))

(defn find-release-from-files [files]
  (when (seq files)
    (let [artist   (-> (first files) :tags :artist first)
          album    (-> (first files) :tags :album  first)
          releases (find-releases artist album)]
      (println "artist, album from tags:" artist "-" album)
      (pick-release (-> releases :resp :search :searchresults :results)
                    files))))

(defn guess-sort-name [name]
  (if-let [the (re-find #"^[Th][Hh][Ee] " name)]
    (str (subs name 4) ", " (subs the 0 3))
    name))

(defn parse-recording [release-id recording]
  {:type       "recording"
   :id         (str release-id "-" (-> recording :position))
   :release-id release-id
   :duration   (-> recording :duration)
   :title      (-> recording :title)
   :position   (-> recording :position)})

(defn parse-release
  "Parses a selected search result, returns a set of documents."
  [release]
  (let [id             (-> release :uri release-id-from-url)
        first-artist   (-> release :artists first)]
    ;(pprint release-data)
    (apply conj
           #{{:type           "release"
              :id             id
              :artist-id      (-> first-artist :id)
              :date           (-> release :released)
              :title          (-> release :title)}
             
             {:type "artist"
              :id             (-> first-artist :id)
              :name           (-> first-artist :name)
              :sort-name      (-> first-artist :name guess-sort-name)}}
           
           (map (partial parse-recording id)
                (-> release :tracklist)))))