(ns kopiplayer.json
  (:require [clojure.java.io :as io]
            [clj-http.client   :as http]
            [clojure.data.json :as json])
  (:import java.util.Properties))

(defn get-version [dep]
  (or
   (System/getProperty (str (name dep) ".version"))
   ;; when in jar
   (let [path (str "META-INF/maven/" (or (namespace dep) (name dep))
                   "/" (name dep) "/pom.properties")
        props (io/resource path)]
    (when props
      (with-open [stream (io/input-stream props)]
        (let [props (doto (Properties.) (.load stream))]
          (.getProperty props "version")))))))

(defn request [url]
  (println "json request:" url)  
  (let [result (http/get 
                url {:client-params 
                     {"http.useragent" 
                      (str "kopiplayer " (get-version 'kopiplayer))}})]
    (println "result:" (:body result))
    (json/read-str (:body result)
                   :key-fn keyword)))