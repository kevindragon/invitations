(ns invitations.db.core
  (:require
    [invitations.libs.datetime :refer [now]]
    [yesql.core :refer [defqueries]]
    [environ.core :refer [env]]
    [korma.db :refer [sqlite3 defdb]])
  (:use [korma.core]))

(def conn
  {:classname      "org.sqlite.JDBC"
   :connection-uri (:database-url env)
   :naming         {:keys   clojure.string/lower-case
                    :fields clojure.string/upper-case}})

(defdb db (sqlite3 {:db (env :db-path)}))

(defentity
  user-stash
  (table :user_stash)
  (pk :id)
  (database db))

(defn save-user-stash [vs]
  (let [vs (assoc vs
             :create_at
             (or (get vs :create_at) (now)))]
    (insert user-stash (values vs))))

(defentity
  user
  (pk :id)
  (database db)
  (fields :id :email))

(defn get-user [vs]
  (select user (where vs)))