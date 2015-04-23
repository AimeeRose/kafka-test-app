(ns kafka-test-app.twitter
  (:use
   [twitter.oauth]
   [twitter.callbacks]
   [twitter.callbacks.handlers]
   [twitter.api.streaming])
  (:require
   [cheshire.core :as json]
   [http.async.client :as ac])
  (:import
   (twitter.callbacks.protocols AsyncStreamingCallback)))

(def my-creds (make-oauth-creds (System/getenv "TWITTER_CONSUMER_API_KEY")
                                (System/getenv "TWITTER_CONSUMER_SECRET")
                                (System/getenv "TWITTER_ACCESS_TOKEN")
                                (System/getenv "TWITTER_TOKEN_SECRET")))

;; everytime there is a callback, check if there is an \r\n in the payload.
;; if yes, print tweet-text + payload and rebind tweet-text to nil
;; if not, rebind tweet-text to tweet-text payload

(def tweet-text (ref nil))

(let [callback (AsyncStreamingCallback.
                 (fn [_resp payload]
                  (let [str-msg (String. (.toByteArray payload))]
                    (if (re-find #"\r\n" str-msg)
                      (do
                        (println (json/parse-string (str @tweet-text payload) true))
                        (dosync (ref-set tweet-text nil)))
                      (do
                        (dosync (ref-set tweet-text (str @tweet-text payload)))))))
                 (fn [_resp]
                   (println "error"))
                 (fn [_resp ex]
                   (.printStackTrace ex)))]
  (statuses-filter
    :params {:track "science"}
    :oauth-creds my-creds
    :callbacks callback))
