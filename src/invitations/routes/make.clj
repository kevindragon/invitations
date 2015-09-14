(ns invitations.routes.make
  (:require [invitations.layout :as layout]
            [compojure.core :refer [defroutes GET]]))

(defn make-card [type]
  (layout/render "make.html" {:type type}))

(defroutes make-routes
  (GET "/make/:type" [type] (make-card type)))