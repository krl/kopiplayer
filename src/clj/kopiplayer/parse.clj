(ns kopiplayer.parse
  (:use [clojure.java.io]
        [kopiplayer.debug])
  (:require [digest])
  (:import [org.jaudiotagger.audio AudioFileIO]
           [org.jaudiotagger.tag FieldKey]
           [org.jaudiotagger.tag Tag]))

(def fields
  (apply conj {} (map (fn [n] [(keyword (. (. n toString) toLowerCase)) n]) (. FieldKey values))))

(defn tags [file]
  (let [tag (. file (getTag))]
    (into {}
          (filter (fn [[name val]] 
                    (and val (not (every? empty? val))))
                  (map (fn [[name val]]
                         [name (seq (map #(. % getContent) (. tag (getFields val))))])
                       fields)))))

(defn subdirectories [path]
  (filter #(. % isDirectory)
          (file-seq (as-file path))))

(defn unique-tag-values [tag files]
  (set (mapcat #(tag (:tags %)) files)))

(defn parse-file [file]
  {:type   "file"
   :id     (digest/sha1 file)
   :tags   (tags (AudioFileIO/read file))
   :path   (. file getPath)})

(defn files-in-folder [folder]
  (reduce
   (fn [coll file]
     (try
       (conj coll
             (parse-file file))
       ;; simply ignore files that do not parse
       (catch org.jaudiotagger.audio.exceptions.CannotReadException e
         coll)
       (catch java.io.FileNotFoundException e
         coll)))
   '()
   (.listFiles folder)))