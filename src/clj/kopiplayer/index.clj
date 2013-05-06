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