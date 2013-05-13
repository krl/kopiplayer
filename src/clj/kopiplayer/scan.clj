(ns kopiplayer.scan
  (:use     [clojure.pprint]
            [kopiplayer.messages :only [defhandler]])
  (:require [kopiplayer.messages :as msg]
            [kopiplayer.parse    :as parse]
            [kopiplayer.discogs  :as discogs]))

(defn scan-path [path]
  (let [directories (parse/subdirectories path)]
    directories))

(def testfiles
  (parse/files-in-folder
   (nth (scan-path "/home/krl/nicotine") 3)))

(def testresult
  (discogs/find-release-from-files testfiles))

(discogs/parse-release testresult)

;; handlers

(defhandler scan-view [state msg]
  (msg/message (:channel state)
               :scan-view))

(pprint testresult)

{:labels
 [{:id 34552,
   :resource_url "http://api.discogs.com/labels/34552",
   :catno "ace 039",
   :name "Ace Fu Records",
   :entity_type ""}],
 :master_id 50412,
 :status "Accepted",
 :country "US",
 :formats [{:descriptions ["Album"], :name "CD", :qty "1"}],
 :master_url "http://api.discogs.com/masters/50412",
 :images
 [{:uri "http://api.discogs.com/image/R-841219-1164315362.jpeg",
   :height 350,
   :width 350,
   :resource_url
   "http://api.discogs.com/image/R-841219-1164315362.jpeg",
   :type "secondary",
   :uri150
   "http://api.discogs.com/image/R-150-841219-1164315362.jpeg"}],
 :artists
 [{:join "",
   :name "Man Man",
   :anv "",
   :tracks "",
   :role "",
   :resource_url "http://api.discogs.com/artists/591940",
   :id 591940}],
 :styles ["Art Rock" "Indie Rock" "Experimental"],
 :companies [],
 :released "2006-02-21",
 :year 2006,
 :resource_url "http://api.discogs.com/releases/841219",
 :videos
 [{:duration 219,
   :description "Push the Eagle's Stomach",
   :embed true,
   :uri "http://www.youtube.com/watch?v=L12RlcFsTLA",
   :title "Push the Eagle's Stomach"}],
 :title "Six Demon Bag",
 :thumb "http://api.discogs.com/image/R-150-841219-1164315362.jpeg",
 :uri "http://www.discogs.com/Man-Man-Six-Demon-Bag/release/841219",
 :data_quality "Correct",
 :community
 {:status "Accepted",
  :rating {:count 7, :average 4.71},
  :have 56,
  :contributors
  [{:username "Durak",
    :resource_url "http://api.discogs.com/users/Durak"}
   {:username "drij",
    :resource_url "http://api.discogs.com/users/drij"}],
  :want 11,
  :submitter
  {:username "Durak",
   :resource_url "http://api.discogs.com/users/Durak"},
  :data_quality "Correct"},
 :tracklist
 [{:duration "2:08", :position "1", :title "Feathers"}
  {:duration "3:33", :position "2", :title "Engrish Bwudd"}
  {:duration "2:54", :position "3", :title "Banana Ghost"}
  {:duration "0:58",
   :position "4",
   :title "Young Einstein On The Beach"}
  {:duration "3:46",
   :position "5",
   :extraartists
   [{:join "",
     :name "Alejandro Borg",
     :anv "",
     :tracks "",
     :role "Marimba, Guitar [Spaghetti]",
     :resource_url "http://api.discogs.com/artists/677480",
     :id 677480}
    {:join "",
     :name "G. Clinton Killingsworth",
     :anv "",
     :tracks "",
     :role "Melodica",
     :resource_url "http://api.discogs.com/artists/677479",
     :id 677479}
    {:join "",
     :name "San Dañon Pisto",
     :anv "",
     :tracks "",
     :role "Saxophone [Healing], Flute",
     :resource_url "http://api.discogs.com/artists/677476",
     :id 677476}
    {:join "",
     :name "Jesse Moynihan",
     :anv "",
     :tracks "",
     :role "Violin",
     :resource_url "http://api.discogs.com/artists/677475",
     :id 677475}],
   :title "Skin Tension"}
  {:duration "4:59",
   :position "6",
   :extraartists
   [{:join "",
     :name "Lulu Nader",
     :anv "",
     :tracks "",
     :role "Vocals [Plush Throat]",
     :resource_url "http://api.discogs.com/artists/677478",
     :id 677478}
    {:join "",
     :name "Crystal Stokowski",
     :anv "",
     :tracks "",
     :role "Vocals [Second Grade Recital Throat]",
     :resource_url "http://api.discogs.com/artists/677477",
     :id 677477}],
   :title "Black Mission Goggles"}
  {:duration "1:26",
   :position "7",
   :extraartists
   [{:join "",
     :name "Pattner",
     :anv "",
     :tracks "",
     :role "Vocals [Manic Throat]",
     :resource_url "http://api.discogs.com/artists/677481",
     :id 677481}
    {:join "",
     :name "Lulu Nader",
     :anv "",
     :tracks "",
     :role "Vocals [Plush Throat]",
     :resource_url "http://api.discogs.com/artists/677478",
     :id 677478}],
   :title "Hot Bat"}
  {:duration "3:39",
   :position "8",
   :extraartists
   [{:join "",
     :name "San Dañon Pisto",
     :anv "",
     :tracks "",
     :role "Saxophone [Healing], Flute",
     :resource_url "http://api.discogs.com/artists/677476",
     :id 677476}
    {:join "",
     :name "Lulu Nader",
     :anv "",
     :tracks "",
     :role "Vocals [Plush Throat]",
     :resource_url "http://api.discogs.com/artists/677478",
     :id 677478}],
   :title "Push The Eagles Stomach"}
  {:duration "3:05",
   :position "9",
   :extraartists
   [{:join "",
     :name "Alejandro Borg",
     :anv "",
     :tracks "",
     :role "Marimba, Guitar [Spaghetti]",
     :resource_url "http://api.discogs.com/artists/677480",
     :id 677480}],
   :title "Spider Cider"}
  {:duration "3:44",
   :position "10",
   :extraartists
   [{:join "",
     :name "Jesse Moynihan",
     :anv "",
     :tracks "",
     :role "Violin",
     :resource_url "http://api.discogs.com/artists/677475",
     :id 677475}],
   :title "Van Helsing Boom Box"}
  {:duration "5:25",
   :position "11",
   :extraartists
   [{:join "",
     :name "San Dañon Pisto",
     :anv "",
     :tracks "",
     :role "Saxophone [Healing], Flute",
     :resource_url "http://api.discogs.com/artists/677476",
     :id 677476}
    {:join "",
     :name "Lulu Nader",
     :anv "",
     :tracks "",
     :role "Vocals [Plush Throat]",
     :resource_url "http://api.discogs.com/artists/677478",
     :id 677478}],
   :title "Tunneling Through The Guy"}
  {:duration "0:04", :position "12", :title "Fishstick Gumbo"}
  {:duration "4:45",
   :position "13",
   :extraartists
   [{:join "",
     :name "San Dañon Pisto",
     :anv "",
     :tracks "",
     :role "Saxophone [Healing], Flute",
     :resource_url "http://api.discogs.com/artists/677476",
     :id 677476}
    {:join "",
     :name "Lulu Nader",
     :anv "",
     :tracks "",
     :role "Vocals [Plush Throat]",
     :resource_url "http://api.discogs.com/artists/677478",
     :id 677478}],
   :title "Ice Dogs"}],
 :released_formatted "21 Feb 2006",
 :genres ["Rock"],
 :series [],
 :id 841219,
 :extraartists
 [{:join "",
   :name "Steven Dufala",
   :anv "",
   :tracks "",
   :role "Artwork By",
   :resource_url "http://api.discogs.com/artists/690684",
   :id 690684}
  {:join "",
   :name "Sergei Sogay",
   :anv "",
   :tracks "",
   :role
   "Bass [Bedeviled], Melodica, Percussion, Synthesizer, Vocals [Cave Man Throat], Other [Vibes]",
   :resource_url "http://api.discogs.com/artists/677465",
   :id 677465}
  {:join "",
   :name "Les Mizzle",
   :anv "",
   :tracks "",
   :role
   "Bass, Percussion, Guitar, Synthesizer [Synthmagic], Vocals [Bizzare Throat], Other [Color, Science]",
   :resource_url "http://api.discogs.com/artists/677464",
   :id 677464}
  {:join "",
   :name "Pow Pow",
   :anv "",
   :tracks "",
   :role
   "Percussion [Sexual Trapkit], Other [Bedroom Eyes, Spirit Healer], Vocals [Coyote Throat]",
   :resource_url "http://api.discogs.com/artists/677463",
   :id 677463}
  {:join "",
   :name "Craig Van Hise",
   :anv "",
   :tracks "",
   :role "Producer",
   :resource_url "http://api.discogs.com/artists/677462",
   :id 677462}
  {:join "",
   :name "Man Man",
   :anv "",
   :tracks "",
   :role "Producer",
   :resource_url "http://api.discogs.com/artists/591940",
   :id 591940}
  {:join "",
   :name "Blanco (3)",
   :anv "",
   :tracks "",
   :role
   "Trumpet, Guitar [Snake], Marimba, Piano, Clarinet, Horn [E Flat], Cello, Vocals [Psycho Throat], Other [Science]",
   :resource_url "http://api.discogs.com/artists/677466",
   :id 677466}
  {:join "",
   :name "Honus Honus",
   :anv "",
   :tracks "",
   :role
   "Vocals [Forethroat], Keyboards [Primitive Rhodes, Clav], Guitar [Dusty], Piano, Organ, Synthesizer [Synthflavours], Percussion, Other [Science, Chair]",
   :resource_url "http://api.discogs.com/artists/677467",
   :id 677467}]}
nil