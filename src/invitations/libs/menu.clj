(ns invitations.libs.menu)

(defn with [v & conds]
  (reduce
    (fn [acc [a b c d]]
      (if (and (empty? (get acc a)) (= b v))
        (assoc acc a c)
        (assoc acc a d)))
    {} conds))

(defn menu-actives [uri]
  (with uri
        [:home "/" " active" ""]
        [:wedding "/make/wedding" " active" ""]
        [:birthday "/make/birthday" " active" ""]
        [:login "/login" " active" ""]
        [:join "/join" " active" ""]
        [:logout "/logout" " active" ""]))
