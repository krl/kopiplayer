(defproject kopiplayer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.2.2"]

                 [aleph "0.3.0-beta15" :exclusions [lamina]]
                 [lamina "0.5.0-beta15"]
                 [ring "1.1.7"]
                 [compojure "1.1.5"]
                 
                 [org/jaudiotagger "2.0.3"]
                 [clucy "0.4.0"]
                 [digest "1.4.3"]
                 [clj-http "0.7.1"]
                 
                 ;; clojurescript
                 [crate "0.2.4"]
                 [jayq "2.3.0"]]
  :plugins [[lein-cljsbuild "0.3.0"]]
  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]
  :cljsbuild { 
    :builds {
      :main {
        :source-paths ["src/cljs"]
        :compiler {:output-to "resources/public/js/cljs.js"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}})