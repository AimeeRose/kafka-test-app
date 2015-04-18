(ns kafka-test-app.core
  (:gen-class))
(use 'clj-kafka.core)
(use 'clj-kafka.zk)
(use 'clj-kafka.producer)
(use 'clj-kafka.consumer.zk)

(brokers {"zookeeper.connect" "127.0.0.1:2181"})

(def p (producer {"metadata.broker.list" "localhost:9092"
                  "serializer.class" "kafka.serializer.DefaultEncoder"
                  "partitioner.class" "kafka.producer.DefaultPartitioner"}))

(send-message p (message "test1" (.getBytes "this is my first message")))
(send-message p (message "test1" (.getBytes "this is my second message")))

(def config {"zookeeper.connect" "localhost:2181"
             "group.id" "clj-kafka.consumer"
             "auto.offset.reset" "smallest"
             "auto.commit.enable" "false"})

(defn string-value
  [k]
  (fn [m]
    (String. (k m) "UTF-8")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (with-resource [c (consumer config)]
    shutdown
    (let [messages (take 10 (messages c "test1"))]
      (println ((string-value :value) (last messages))))))
