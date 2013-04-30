(ns kopiplayer.server
  (:use [clojure.java.io]
        [kopiplayer.debug]
        [lamina.core]
        [ring.middleware.resource]
        [compojure.core]
        [aleph.http])
  (:require [compojure.route  :as route]
            [kopiplayer.index :as index]))

(defn messages [messages]
  [:messages messages])

(defn message [& stuff]
  (vec stuff))

(defn send-message [ch message]
  (enqueue ch (pr-str message)))

(defn send-artist-info [ch id]
  (send-message ch [:artist-info (index/artist-info id)]))

(defn play-recording [ch id]
  (let [recording (index/get-id id)
        file      (first (index/search "recording-id:" (:id recording)))]    
    (send-message ch [:play (:id file)])))

(defn handle-message [state msg]
  (println "handle message" msg)
  (let [[command & args] (read-string msg)]
    (case command
      :artist-info    (send-artist-info (:channel state) 
                                        (first args))
      :play-recording (play-recording   (:channel state)
                                        (first args)))    
    (receive (:channel state)
             (partial handle-message state))))

(def latest-session* (atom nil))

(defn init-client
  "Sends the data the client needs to prepare the frontend"
  [ch]
  (send-message ch [:artists (all-artists)]))

(defn init-session [response-ch _]
  (reset! latest-session* response-ch)
  (receive response-ch
           (partial handle-message
                    ;; initial state
                    {:channel response-ch}))
  (init-client response-ch))

(defn deliver-file [id]
  (let [file (index/get-id id)]
    {:status 200
     :headers {"Content-Type" "audio/ogg"}
     :body (as-file (:path file))}))

(defroutes app-routes
  (GET "/socket" []
    (wrap-aleph-handler init-session))
  (GET ["/"] {} (slurp "resources/index.html"))
  (GET "/play/:id" [id] (deliver-file id))
  (route/resources "/resources/public")
  (route/not-found "Page not found"))

(def server* (atom nil))

(defn start-server []
  (when @server* (@server*))
  (reset! server* (start-http-server 
                   (wrap-ring-handler app-routes)
                   {:port 8082 :websocket true})))

(start-server)