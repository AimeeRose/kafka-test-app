(ns kafka-test-app.producer)

(use 'clj-kafka.producer)

(def p (producer {"metadata.broker.list" "localhost:9092"
                  "serializer.class" "kafka.serializer.DefaultEncoder"
                  "partitioner.class" "kafka.producer.DefaultPartitioner"}))

(defn produce [topic]
  (doseq [line (line-seq (java.io.BufferedReader. *in*))] 
    (send-message p (message topic (.getBytes line)))))
