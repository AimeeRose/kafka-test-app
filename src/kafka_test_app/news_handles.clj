(ns kafka-test-app.news-handles
  (:use
   [twitter.oauth]
   [twitter.callbacks]
   [twitter.callbacks.handlers]
   [twitter.api.restful]))

(def my-creds (make-oauth-creds (System/getenv "TWITTER_CONSUMER_API_KEY")
                                (System/getenv "TWITTER_CONSUMER_SECRET")
                                (System/getenv "TWITTER_ACCESS_TOKEN")
                                (System/getenv "TWITTER_TOKEN_SECRET")))

(defn get-user [screen-name]
  (users-show :oauth-creds my-creds :params {:screen-name screen-name}))

; http://memeburn.com/2010/09/the-100-most-influential-news-media-twitter-accounts/
(def news-handles '(
  "mashable"
  "cnnbrk"
  "big_picture"
  "theonion"
  "time"
  "breakingnews"
  "bbcbreaking"
  "espn"
  "harvardbiz"
  "gizmodo"
  "techcrunch"
  "wired"
  "wsj"
  "smashingmag"
  "pitchforkmedia"
  "rollingstone"
  "whitehouse"
  "cnn"
  "tweetmeme"
  "peoplemag"
  "natgeosociety"
  "nytimes"
  "lifehacker"
  "foxnews"
  "waitwait"
  "newsweek"
  "huffingtonpost"
  "newscientist"
  "mental_floss"
  "theeconomist"
  "emarketer"
  "engadget"
  "cracked"
  "slate"
  "bbcclick"
  "fastcompany"
  "reuters"
  "incmagazine"
  "eonline"
  "rww"
  "gdgt"
  "instyle"
  "mckquarterly"
  "enews"
  "nprnews"
  "usatoday"
  "mtv"
  "freakonomics"
  "boingboing"
  "billboarddotcom"
  "empiremagazine"
  "todayshow"
  "good"
  "gawker"
  "msnbc_breaking"
  "cbsnews"
  "guardiantech"
  "usweekly"
  "life"
  "sciam"
  "pastemagazine"
  "drudge_report"
  "parisreview"
  "latimes"
  "telegraphnews"
  "abc7"
  "arstechnica"
  "cnnmoney"
  "nprpolitics"
  "nytimesphoto"
  "nybooks"
  "nielsenwire"
  "io9"
  "sciencechannel"
  "usabreakingnews"
  "vanityfairmag"
  "cw_network"
  "bbcworld"
  "abc"
  "themoment"
  "socialmedia2day"
  "slashdot"
  "washingtonpost"
  "tpmmedia"
  "msnbc"
  "wnycradiolab"
  "cnnlive"
  "davos"
  "planetmoney"
  "cnetnews"
  "politico"
  "tvnewser"
  "guardiannews"
  "yahoonews"
  "seedmag"
  "tvguide"
  "travlandleisure"
  "newyorkpost"
  "discovermag"
  "sciencenewsorg"))

(defn handle-id [screen-name]
  (:id (:body (get-user screen-name))))

;(def news-handle-ids (map handle-id news-handles))

; mashable through freakonomics
(def news-handles-ids '(972651 428333 18735898 14075928 14293310 6017542 5402612 2557521 14800270 2890961 816653 1344951 3108351 15736190 2707054218 14780915 30313925 759251 11883132 3064617094 300974581 807095 7144422 1367531 13784322 2884771 14511951 19658826 20065936 5988062 21217761 14372486 12513472 15164565 7400702 2735591 1652541 476158046 2883841 4641021 15738725 14934818 15308469 22179997 5392522 15754281 2367911 14514804))
