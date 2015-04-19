(ns kafka-test-app.simple-consumer)

(use 'clj-kafka.consumer.simple)

(def client-id "kafka-test-app-22")

(def c (consumer "localhost" 9092 client-id))

(defn string-value
  [k]
  (fn [m]
    (String. (k m) "UTF-8")))

(defn fetch-message [topic offset]
  (first (messages c client-id topic 0 offset (* 300 1024))))

(defn consume [topic]
  "consume topic and print to stdout"
  ;; if there are messages, print each in turn
  ;; otherwise sleep a bit and recur
  ;;
  (loop [offset 0]
    (def msg (fetch-message topic offset))
    ;; if there is a message, print it and increment offset
    ;; 
    (if (= (type msg) clj_kafka.core.KafkaMessage)
      (do
        (println ((string-value :value) msg))
        (recur (inc offset)))
      (do
        (Thread/sleep 1000)
        (recur offset)))))
