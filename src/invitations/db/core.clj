(ns invitations.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [environ.core :refer [env]]
    [korma.db :refer [sqlite3 defdb]]))

(def conn
  {:classname      "org.sqlite.JDBC"
   :connection-uri (:database-url env)
   :naming         {:keys   clojure.string/lower-case
                    :fields clojure.string/upper-case}})

(defdb db (sqlite3 {:db (env :db-path)}))