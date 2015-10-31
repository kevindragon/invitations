(ns invitations.libs.image
  (:require
    [invitations.libs.file :refer
     [gen-res-suitable-filename resource-prefix explode-file-path
      truncate-to-resource-path truncate-to-public-path]]
    [image-resizer.resize :refer [resize-width-fn]]
    [image-resizer.format :as format]
    [clojure.java.io :as io]))

(defn create-suitable-images [filename]
  (let [[suitable-path suitable-filename]
        (gen-res-suitable-filename "640" filename)]
    (-> (io/file (resource-prefix filename))
        ((resize-width-fn 640))
        (format/as-file (resource-prefix
                          (str suitable-path suitable-filename)))
        truncate-to-resource-path
        truncate-to-public-path
        explode-file-path)))