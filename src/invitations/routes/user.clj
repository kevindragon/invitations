(ns invitations.routes.user
  (:require [invitations.layout :as layout]
            [invitations.libs.validator :as v]
            [invitations.libs.user
             :refer [stash-register get-user write-session]]
            [ring.util.response :refer [redirect]]
            [compojure.core :refer [defroutes GET POST]]
            [bouncer.validators :refer [defvalidator required]]
            [invitations.libs.mail :as mail]))

(defn login [req]
  (layout/render "user/login.html" req))

(defn login-post [email password req]
  (let [user (get-user email password)]
    (if user
      (-> (redirect "/")
          (write-session user))
      (layout/render
        "user/login.html"
        req
        {:warning-msg "用户不存在或密码错误"
         :email       email
         :password    password}))))

(defn join [req]
  (layout/render "user/join.html" req))

(defn join-post [email password req]
  (let [msg (or (v/email email)
                (v/password password))]
    (if (not (empty? msg))
      (layout/render "user/join.html" req
                     {:warning-msg msg
                      :email email
                      :password password})
      (let [domain (mail/domain-of email)]
        (stash-register email password)
        (layout/render "user/stash.html" req
                       {:mail-domain domain})))))

(defn logout []
  (->
    (redirect "/")
    (assoc-in [:session :user] nil)))

(defn user [req]
  (layout/render "user/user.html" req))

(defroutes
  user-routes
  (GET "/login" req (login req))
  (POST "/login" [email password :as req] (login-post email password req))
  (GET "/join" req (join req))
  (POST "/join" [email password :as req] (join-post email password req))
  (GET "/logout" [] (logout))
  (GET "/user" req (user req)))