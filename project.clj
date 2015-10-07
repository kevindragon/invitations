(defproject invitations "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [selmer "0.9.2"]
                 [com.taoensso/timbre "4.1.1"]
                 [com.taoensso/tower "3.0.2"]
                 [markdown-clj "0.9.74"]
                 [environ "1.0.1"]
                 [compojure "1.4.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring "1.4.0"
                  :exclusions [ring/ring-jetty-adapter]]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "0.3.3"]
                 [prone "0.8.2"]
                 [org.clojure/tools.nrepl "0.2.11"]
                 [org.webjars/bootstrap "3.3.5"]
                 [org.webjars/jquery "2.1.4"]
                 [migratus "0.8.4"]
                 [conman "0.1.9"]
                 [org.xerial/sqlite-jdbc "3.8.11.1"]
                 [org.immutant/web "2.1.0"]
                 [korma "0.4.2"]
                 [pandect "0.5.4"]
                 [image-resizer "0.1.8"]
                 [bouncer "0.3.3"]
                 [clj-time "0.11.0"]
                 [com.draines/postal "1.11.3"]]

  :min-lein-version "2.0.0"
  :uberjar-name "invitations.jar"
  :jvm-opts ["-server"]

  :main invitations.core
  :migratus {:store :database}

  :plugins [[lein-environ "1.0.1"]
            [migratus-lein "0.1.7"]]
  :profiles
  {:uberjar {:omit-source true
             :env {:production true
                   :db-path "invitations.db"
                   :database-url "jdbc:sqlite:invitations.db"}
             :aot :all}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.7.0"]
                                 [mvxcvi/puget "0.8.1"]]
                  
                  :repl-options {:init-ns invitations.core}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]
                  ;;when :nrepl-port is set the application starts the nREPL server on load
                  :env {:dev        true
                        :port       3000
                        :nrepl-port 7000}}
   :project/test {:env {:test       true
                        :port       3001
                        :nrepl-port 7001}}
   :profiles/dev {}
   :profiles/test {}})
