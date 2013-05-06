(ns kopiplayer.library
  (:require [kopiplayer.messages :as msg]
            [kopiplayer.index :as idx]))

(defn artist-info
  "Gets the releases and recordings from this artist"
  [id]
  (let [releases (idx/search "type:release AND artist-id:" id)
        with-recordings
        (map (fn [release]
               (assoc release :recordings
                      (sort-by #(Integer. (:number %))
                               (idx/search "type:recording AND "
                                       "release-id:" (:id release)))))
             releases)]
    (assoc (idx/get-id id)
      :releases (sort-by :date with-recordings))))

(defn library 
  "Returns an alphabetially sorted list of artists."
  []
  (sort-by 
   first
   (seq
    (reduce (fn [artists artist]
              (let [key (-> artist :sort-name first str keyword)]              
                (assoc artists key 
                       (sort-by :sort-name
                                (conj (or (key artists) '())
                                      artist)))))
            {}
            (idx/search "type:artist")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; handlers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defhandler display-library [state msg]
  (msg/message (:channel state)
               :display-library
               (library)))

(defhandler display-artist-info [state msg]
  (msg/message (:channel state)
               :display-artist-info
               (artist-info msg)))