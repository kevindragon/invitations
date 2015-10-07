(ns invitations.libs.datetime
  (:require [clj-time.core :as t]))

(defn now [] (t/now))