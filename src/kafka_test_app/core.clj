(ns kafka-test-app.core
  (:require [kafka-test-app.producer :as producer])
  (:require [kafka-test-app.simple-consumer :as consumer]))
(use 'clj-kafka.core)
(use 'clj-kafka.zk)

(brokers {"zookeeper.connect" "127.0.0.1:2181"})

(defn -main
  "either producer or consume topic
   ex.: lein run producer topic1
   ex.: lein run consumer topic1"
  [producer-consumer topic]
  (if (= producer-consumer "consumer")
    (consumer/consume topic)
    (producer/produce topic)))
