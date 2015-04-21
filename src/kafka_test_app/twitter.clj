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

(defn parse-tweet-body [body]
  (slurp (clojure.java.io/input-stream (.toByteArray body))))

; Credit https://gist.github.com/samn/6231768
(defn print-tweet
   "A streaming on-bodypart handler.
    baos is a ByteArrayOutputStream.  (str baos) is the response body (encoded as JSON).
    This handler will print the expanded media URL of Tweets that have media."
   [response body]
   ;; parse the tweet (true means convert to keyword keys)
   (let [tweet (parse-tweet-body body)
         ;; retrieve the media array from the tweet.
         ;; see https://dev.twitter.com/docs/tweet-entities
         media (get-in tweet [:entities :media])
         urls (map :expanded_url media)]
    (println tweet)
    ;; doseq can include a conditional inline with its binding
    ;; we'll only iterate and print when urls isn't empty
    (doseq [url urls :when (not (empty? urls))]
      (println url))))

; supply a callback that only prints the text of the status
(def ^:dynamic 
   *custom-streaming-callback* 
   (AsyncStreamingCallback.
     print-tweet
     (comp println response-return-everything)
     exception-print))

(statuses-filter :params {:track "dogs"}
         :oauth-creds my-creds
         :callbacks *custom-streaming-callback*)
