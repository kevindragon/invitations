(ns invitations.libs.validator
  (:require [invitations.i18n :as i18n]))

(defn required? [value]
  (if (string? value)
    (not (empty? value))
    (not (nil? value))))

(defn contain-uppercase? [^String value]
  (boolean (re-find #"[A-Z]" value)))

(defn contain-lowercase? [^String value]
  (boolean (re-find #"[a-z]" value)))

(defn contain-number? [^String value]
  (boolean (re-find #"[0-9]" value)))

(defn contain-alphabet? [^String value]
  (and (contain-lowercase? value)
       (contain-uppercase? value)))

(defn email-format? [^String value]
  (boolean (re-matches #"^[^@]+@[^@\\.]+[\\.].+" value)))

(defn validate [name & conds]
  (let [invalid (drop-while (fn [[f _]] (println f) (f name))
                            (partition 2 conds))]
    (when (> (count invalid) 0)
      (second (first invalid)))))

(defn email [email]
  (validate
    email
    required? (i18n/t :user :email-required)
    email-format? (i18n/t :user :invalid-email)))

(defn password [password]
  (validate
    password
    required? (i18n/t :user :pwd-required)
    contain-lowercase? (i18n/t :user :pwd-require-lowercase)
    contain-uppercase? (i18n/t :user :pwd-require-uppercase)
    contain-number? (i18n/t :user :pwd-require-number)))