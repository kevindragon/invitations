(ns invitations.routes.make
  (:require [invitations.layout :as layout]
            [invitations.libs.user :refer [get-user-id]]
            [invitations.libs.file :refer [copy-file gen-res-filename]]
            [invitations.libs.image :refer [create-suitable-images]]
            [compojure.core :refer [defroutes GET POST]]
            [pandect.algo.md5 :refer [md5]]))

(defn template-file [type]
  (str "make/" type ".html"))

(defn make-card [type]
  (layout/render (template-file type)))

(defn preview []
  (layout/render "make/preview.html"))

(defn generate-filename
  "生成文件路径和文件名"
  [filename]
  (gen-res-filename (str (get-user-id) "/") filename))

(defn upload-banner [{:keys [tempfile size filename]}]
  (let [[output-path output-filename] (generate-filename filename)
        copy-ok? (copy-file tempfile (str output-path output-filename))]
    (if copy-ok?
      (let [[url-path url-filename]
            (create-suitable-images (str output-path output-filename))]
        {:body
         {:files [{:name (str "/" url-path url-filename)
                   :size size
                   :url (str "/" url-path url-filename)
                   :thumbnailUrl (str "/" url-path url-filename)}]}})
      {:body
       { :files [{:name filename
                  :size size
                  :error "Upload error."}]}})))

(defroutes
  make-routes
  (GET "/preview" [] (preview))
  (GET "/make/:type" [type] (make-card type))
  (POST "/upload/banner" [banner_images] (upload-banner banner_images)))