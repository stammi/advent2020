(ns advent.passwords
  (:require [clojure.string :as str]))

(defn sled-valid [from to letter password]
  (<= from (count (filter #(= % letter) password)) to))

(defn toboggan-valid [from to letter password]
  (let [from-letter (get password (dec from))
        to-letter   (get password (dec to))]
    (and (not (= to-letter from-letter))
         (or (= letter from-letter) (= letter to-letter)))))

(defn is-valid [val-fn db-string]
  (let [[from-to letter password] (str/split db-string #" ")
        [from to] (map #(Integer/parseInt %) (str/split from-to #"-"))
        letter (first letter)]
    (val-fn from to letter password)))

(defn day2 [_opts]
  (let [db                  (str/split-lines (slurp "resources/passwords.db"))
        valid-sled-shop     (count (filter (partial is-valid sled-valid) db))
        valid-toboggan-shop (count (filter (partial is-valid toboggan-valid) db))]
    (println (str "Password validity differs in the two shops.\n"
                  valid-sled-shop " passwords are valid by sled shop rules.\n"
                  valid-toboggan-shop " passwords are valid by toboggan shop rules.\n"))))

