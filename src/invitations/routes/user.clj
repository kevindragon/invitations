(ns invitations.routes.user
  (:require [invitations.layout :as layout]
            [invitations.libs.mail :as mail]
            [compojure.core :refer [defroutes GET POST]]
            [bouncer.validators :refer [defvalidator required]]))

(defn join []
  (layout/render "user/join.html"))

(defvalidator pwd-legal [password]
  (and (required password)
       (re-find #"\d" password)
       (re-find #"[a-z]" password)
       (re-find #"[A-Z]" password)))

(defn validated [b]
  (cond
    b [true (str "验证成功")]
    (not b) [false (str "验证失败")]))

(defn join-post [email password]
  (let [[b msg] (validated (and (mail/is-legal email)
                                (pwd-legal password)))]
    (if b
      (str email " " password)
      (layout/render "user/join.html" {:msg msg}))))

(defroutes
  user-routes
  (GET "/join" [] (join))
  (POST "/join" [email password] (join-post email password)))