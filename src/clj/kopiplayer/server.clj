(ns kopiplayer.server
  (:use [clojure.java.io]
        [kopiplayer.debug]
        [ring.middleware.resource]        
        [compojure.core]
        [aleph.http])
  (:require [lamina.core :as lamina]
            [compojure.route  :as route]
            [kopiplayer.index :as index]
            [kopiplayer.messages :as msg]))

(def latest-session* (atom nil))

(defn message-loop [state]
  (lamina/receive 
   (:channel state)
   (fn [data]
     (let [msg       (read-string data)
           new-state (msg/handle-message state msg)]
       (message-loop (dbg new-state))))))

(defn init-session [ch _]
  (reset! latest-session* ch)
  (message-loop {:channel ch}))

(defn serve-file [id]
  (let [file (index/get-id id)]
    {:status 200
     :headers {"Content-Type" "audio/ogg"}
     :body (as-file (:path file))}))

(defroutes app-routes
  (GET "/socket" []
    (wrap-aleph-handler init-session))
  (GET ["/"] {} (slurp "resources/index.html"))
  (GET "/play/:id" [id] (serve-file id))
  (route/resources "/resources/public")
  (route/not-found "Page not found"))

(def server* (atom nil))

(defn start-server []
  (when @server* (@server*))
  (. Thread (sleep 1000))
  (reset! server* (start-http-server 
                   (wrap-ring-handler app-routes)
                   {:port 2888 :websocket true})))

(start-server)