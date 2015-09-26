(ns invitations.libs.mail
  (:require [bouncer.validators :as v]))

(defn is-legal [email]
  (v/email email))