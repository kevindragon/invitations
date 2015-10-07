(ns invitations.libs.mail
  (:require [postal.core :refer [send-message]]))

(defn send-to [who subject content]
  (send-message
    {:from "Invitations<wenlin1988@126.com>"
     :to who
     :subject subject
     :body content}))

(defn send-verify-mail
  "发送注册的邮箱验证邮件"
  [email code]
  (let [content (str "http://localhost:3000/register/verify/" code)]
    (send-to email "邮箱账号验证" content)))

(defn domain-of [email]
  (let [parts (clojure.string/split email #"@")
        domain (last parts)]
    (cond
      (= domain "126.com") "126.com"
      (= domain "163.com") "mail.163.com")))