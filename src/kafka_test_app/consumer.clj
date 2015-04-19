(ns kafka-test-app.consumer)

(use 'clj-kafka.consumer.zk)
(use 'clj-kafka.core)

(def config {"zookeeper.connect" "localhost:2181"
             "group.id" "clj-kafka.consumer"
             "auto.commit.interval.ms" "10"
             "num.consumer.fetchers" "1"
             "auto.offset.reset" "smallest"})

(def c (consumer config))

(defn string-value
  [k]
  (fn [m]
    (String. (k m) "UTF-8")))

(defn consume [topic]
  "consume topic and print to stdout"
  ;; if there are messages, print each in turn
  ;; otherwise sleep a bit and recur
  ;;
  (let [msgs (messages c topic)]
    (loop [msgs msgs]
      (println ((string-value :value) (first msgs)))
      (recur (rest msgs)))))
