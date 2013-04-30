(ns kopiplayer.musicbrainz
  (:require [clj-http.client   :as http]
            [clojure.data.json :as json])
  (:use [clojure.pprint]
        [clojure.set :only [union]]
        [kopiplayer.debug]
        [kopiplayer.parse :only [unique-tag-values]]))

(defn musicbrainz-request [& parts]  
  (let [url (str (apply str "http://musicbrainz.org/ws/2/" parts)
                 "&fmt=json")]
    (println "musicbrainz request:" url)
    (json/read-str (:body (http/get url)) :key-fn keyword)))

(defn get-release-data [id]
  (musicbrainz-request "release/" id "?inc=recordings+artist-credits"))

(defn get-artist-data [id]
  (musicbrainz-request "artist/" id))

(defn parse-recording [recording]
  {:type       "recording"
   :id         (-> recording :recording :id)
   :length     (-> recording :length)
   :title      (-> recording :title)
   :number     (-> recording :number)})

(defn parse-release
  "Parses a selected search result, returns a set of documents."
  [single-search-result]
  (let [id             (:id single-search-result)
        release-data   (get-release-data id)
        first-artist   (-> release-data :artist-credit first :artist)]
    ;(pprint release-data)
    (apply conj
           #{{:type           "release"
              :id             id
              :artist-id      (-> first-artist :id)
              :date           (-> release-data :date)
              :title          (-> release-data :title)}
             
             {:type "artist"
              :id             (-> first-artist :id)
              :name           (-> first-artist :name)
              :sort-name      (-> first-artist :sort-name)
              :disambiguation (-> first-artist :disambiguation)}}
           
           (map #(assoc (parse-recording %) 
                   :release-id id)
                (-> release-data :media first :tracks)))))

(defn best-release-for-files [releases-json files]
  (println "best-release-for-files")
  (let [with-correct-track-count 
        (filter #(= (:track-count %) (count files))
                releases-json)]
    ;; (assert (= (count with-correct-track-count) 1)
    ;;         "fixme: only one album with correct track length must be found")
    (first with-correct-track-count)))

(defn search-releases [artist album files]
  (let [results  (musicbrainz-request "release"
                                      "?query=release:" album
                                      " AND artist:"    artist)]
     (parse-release 
      (best-release-for-files (:releases results) files))))

(defn indices
  "indices matching pred"
  [pred coll]
  (keep-indexed #(when (pred %2) %1) coll))

(defn bind-files-to-recordings [files documents]
  ;; simply do it with track-nr tag for now
  (apply conj documents
         (map (fn [file]
                (let [file-tracknr (-> file :tags :track first Integer.)
                      recording    (some #(and (= (:type %) "recording")
                                               (= (Integer. (:number %))
                                                  file-tracknr)
                                               %)
                                         documents)]
                  (assoc file :recording-id (:id recording))))
       files)))

(defn identify-release [files]
  (println "identify-files")
  (when (seq files)
    (let [artist           (-> (first files) :tags :artist first)
          album            (-> (first files) :tags :album  first)]
      (println "found: " artist album)
      (bind-files-to-recordings 
       files
       (search-releases artist album files)))))

    ;; files)
    ;;   files)))
