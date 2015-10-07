(ns invitations.libs.user
  (:require [pandect.algo.md5 :refer [md5]]
            [pandect.algo.sha256 :refer [sha256]]
            [invitations.db.core :as db]
            [invitations.libs.mail :as mail]))

(defn get-user-id [req]
  (or
    (get-in req [:session :user :id])
    0))

(defn get-user [email password]
  (let [encrypted (sha256 password)]
    (first (db/get-user
             {:email email,
              :password encrypted}))))

(defn gen-code [& params]
  (md5 (apply str (System/currentTimeMillis) params)))

(defn stash-register [email password]
  (let [code (gen-code email)
        mail-ok? (mail/send-verify-mail email code)]
    (if mail-ok?
      (db/save-user-stash {:email email
                        :password (sha256 password)
                        :code code}))))

(defn write-session [resp user]
  (assoc-in resp [:session :user] user))