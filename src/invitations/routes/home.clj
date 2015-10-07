(ns invitations.routes.home
  (:require [invitations.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]))

(defn home-page [req]
  (layout/render "home.html" req))

(defn about-page [req]
  (layout/render "about.html" req))

(defroutes home-routes
  (GET "/" req (home-page req))
  (GET "/about" req (about-page req)))

