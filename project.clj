(defproject kafka-test-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"],
                 [clj-kafka "0.2.8-0.8.1.1"]]
  :main ^:skip-aot kafka-test-app.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
