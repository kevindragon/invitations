(ns invitations.libs.file
  (:require [clojure.java.io :as io]
            [pandect.algo.md5 :refer [md5]])
  (:import (java.io FileInputStream File FileOutputStream)
           (java.net URLDecoder)))

(def res-public-path "resources/public/")

(def banner-resource-path "banners/")

(defn make-sure-end-with-separator
  "如果路径末尾不是路径分割符，则在路径末尾加上"
  [path]
  (cond
    (.endsWith path File/separator) path
    :else (str path File/separator)))

(defn resource-prefix
  "添加resources/public/目录，如果是绝对路径则不添加"
  [filename]
  (cond
    (.isAbsolute (io/file filename)) filename
    :else (str res-public-path filename)))

(defn strip-prefix-path [filename path]
  (let [path (make-sure-end-with-separator path)]
    (when (.startsWith filename path)
      (clojure.string/replace-first
        filename
        (re-pattern (str "^" path)) ""))))

(defn truncate-to-resource-path [filename]
  (strip-prefix-path filename (System/getProperty "user.dir")))

(defn truncate-to-public-path [filename]
  (strip-prefix-path filename res-public-path))

(defn make-sure-path-str [path]
  (cond
    (.endsWith path File/separator) path
    :else (str path File/separator)))

(defn make-sure-path-exists [path]
  (let [f (io/file path)]
    (when (not (.exists f)) (.mkdirs f))))

(defn extension-name
  "获取文件扩展名"
  [filename]
  (let [parts (clojure.string/split filename #"\.")]
    [(clojure.string/join "" (butlast parts)) (last parts)]))

(defn gen-res-filename
  "生成文件路径和文件名"
  [subdir filename]
  (let [subdir (make-sure-path-str subdir)
        [name ext] (extension-name filename)
        hash-str (md5 (str subdir name))
        path (str banner-resource-path subdir)
        real-path (resource-prefix path)]
    (make-sure-path-exists real-path)
    [path (str hash-str "." ext)]))

(defn explode-file-path
  "把路径和文件名分开"
  [filename]
  (let [f (io/file filename)]
    [(make-sure-end-with-separator (.getParent f)) (.getName f)]))

(defn gen-res-suitable-filename
  "生成便于在移动设备上显示的图片路径的文件名"
  [subdir filename]
  (let [[p name] (explode-file-path filename)
        path (clojure.string/join File/separator [p subdir])]
    (make-sure-path-exists (resource-prefix path))
    [path name]))

(defn file-path
  ([filename]
   (URLDecoder/decode filename "utf-8"))
  ([path filename]
   (URLDecoder/decode
     (str path File/separator filename)
     "utf-8")))

(defn copy-file
  "复制文件"
  [srcFile destFile]
  (try
    (with-open [in (new FileInputStream (resource-prefix srcFile))
                out (new FileOutputStream (file-path (resource-prefix destFile)))]
      (let [source (.getChannel in)
            dest   (.getChannel out)]
        (.transferFrom dest source 0 (.size source))
        (.flush out)))
    (catch Exception e (println e) false))
  true)
