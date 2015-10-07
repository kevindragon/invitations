(ns invitations.i18n)

(def langs
  {:zh-CN {:user
           {:email-required "邮箱不能为空"
            :invalid-email "邮箱格式不正确"
            :invalid-password "密码不正确"
            :invalid-password-format "密码不符合要求"

            :pwd-required "密码不能为空"
            :pwd-require-uppercase "密码必须包含大写字母"
            :pwd-require-lowercase "密码必须包含小写字母"
            :pwd-require-number "密码必须包含数字"}}})

(defmacro t [& params]
  `(apply format
          (get-in langs
                  (cons :zh-CN (take-while keyword? (vector ~@params))))
          (drop-while keyword? (vector ~@params))))