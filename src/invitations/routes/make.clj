(ns invitations.routes.make
  (:require [invitations.layout :as layout]
            [invitations.libs.user :refer [get-user-id]]
            [invitations.libs.file :refer [copy-file gen-res-filename]]
            [invitations.libs.image :refer [create-suitable-images]]
            [compojure.core :refer [defroutes GET POST]]
            [pandect.algo.md5 :refer [md5]]))

(defn template-file [type]
  (str "make/" type ".html"))

(defn make-card [type req]
  (layout/render (template-file type) req))

(defn preview [req]
  (layout/render "make/preview.html" req))

(defn generate-filename
  "生成文件路径和文件名"
  [filename req]
  (gen-res-filename (str (get-user-id req) "/") filename))

(defn upload-banner [{:keys [tempfile size filename]} req]
  (let [[output-path output-filename] (generate-filename filename req)
        copy-ok? (copy-file tempfile (str output-path output-filename))]
    (if copy-ok?
      (let [[url-path url-filename]
            (create-suitable-images (str output-path output-filename))]
        {:body
         {:files {:name (str "/" output-path output-filename)
                  :size size
                  :url (str "/" url-path url-filename)
                  :thumbnailUrl (str "/" url-path url-filename)}}})
      {:body
       { :files {:name filename
                 :size size
                 :error "Upload error."}}})))

(defn save-basic [{:keys [params]}]
  (println params)
  {:body {:status 0}})

(defn upload-gallery [req]
  ;(copy-file tempfile filename)
  (println (:params req))
  {:body [1 2 3]}
  )

(defroutes
  make-routes
  (GET "/preview" req (preview req))
  (GET "/make/:type" [type :as req] (make-card type req))
  (POST "/upload/banner" [banner_images :as req] (upload-banner banner_images req))
  (POST "/make/save/basic" req (save-basic req))
  (POST "/upload/gallery" req (upload-gallery req)))

