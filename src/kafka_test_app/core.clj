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

(def config {"zookeeper.connect" "localhost:2181"
             "group.id" "clj-kafka.consumer"
             "auto.commit.interval.ms" "10"
             "auto.offset.reset" "smallest"
             "auto.commit.enable" "true"})

(defn string-value
  [k]
  (fn [m]
    (String. (k m) "UTF-8")))

(defn consume [topic]
  "consume topic and print to stdout"
  ;; if there are messages, print each in turn
  ;; otherwise sleep a bit and recur
  ;;
  (let [c (consumer config) msgs (messages c topic)]
    (loop [msgs msgs]
      (println ((string-value :value) (first msgs)))
      (recur (rest msgs)))))

(defn produce [topic]
  (doseq [line (line-seq (java.io.BufferedReader. *in*))] 
    (send-message p (message topic (.getBytes line)))))

(defn -main
  "either producer or consume topic
   ex.: lein run producer topic1
   ex.: lein run consumer topic1"
  [producer-consumer topic]
  (if (= producer-consumer "consumer")
    (consume topic)
    (produce topic)))
