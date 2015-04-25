(ns kafka-test-app.producer
  (:require [kafka-test-app.twitter :as twitter]))

(use 'clj-kafka.producer)

(def p (producer {"metadata.broker.list" "localhost:9092"
                  "serializer.class" "kafka.serializer.DefaultEncoder"
                  "partitioner.class" "kafka.producer.DefaultPartitioner"}))

(defn produce [topic]
  (twitter/stream #(send-message p (message topic (.getBytes (:text %))))))
