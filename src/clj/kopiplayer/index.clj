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
                                      (search "type:recording AND "
                                              "release-id:" (:id release))))
                             releases)]
    (assoc (get-id id)
      :releases with-recordings)))

(defn all-artists []
  (reduce (fn [artists artist]
            (let [key (-> artist :sort-name first str keyword)]
              (assoc artists key (conj (or (key artists) '())
                                       artist))))
          {}
          (search "type:artist")))

(first (search "type:artist"))

{:release-id "20ef2364-7fc0-4f4d-a204-746acc4782ac", :type "recording", :id "4b86fe34-2055-4c4c-8414-5364871c0ab7", :length "923706", :title "Köhntarkösz, Part 1", :number "1"}

{:type "release", :id "20ef2364-7fc0-4f4d-a204-746acc4782ac", :artist-id "7f89e84d-4c35-4d53-a965-4f3ce1c4ef4f", :date "1988", :title "Köhntarkösz"}

{:type "recording", :id "20ef2364-7fc0-4f4d-a204-746acc4782ac", :artist-id "7f89e84d-4c35-4d53-a965-4f3ce1c4ef4f", :date "1988", :title "Köhntarkösz"}