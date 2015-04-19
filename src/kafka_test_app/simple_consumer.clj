(ns kafka-test-app.simple-consumer)

(use 'clj-kafka.consumer.simple)

(def client-id "kafka-test-app")

(def c (consumer "localhost" 9092 client-id))

(defn string-value
  [k]
  (fn [m]
    (String. (k m) "UTF-8")))

;; in order for this to work like a group, will need to store offset someplace else
;;
(def offset (ref 0))

(defn fetch-message [topic]
  (println offset)
  (first (messages c client-id topic 0 (deref offset) (* 300 1024))))

(defn consume [topic]
  "consume topic and print to stdout"
  ;; if there are messages, print each in turn
  ;; otherwise sleep a bit and recur
  ;;
  (loop []
    (def msg (fetch-message topic))
    ;; if there is a message, print it and increment offset
    ;; 
    (if (= (type msg) clj_kafka.core.KafkaMessage)
      (do
        (println ((string-value :value) msg))
        (dosync (alter offset inc))
        (recur))
      (do
        (Thread/sleep 1000)
        (recur)))))
