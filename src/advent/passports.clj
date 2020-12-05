(ns advent.passports
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn read-passport [entry]
  (let [raw-pairs (-> entry
                      (str/replace "\n" " ")
                      (str/split #" "))]
    (into {} (map (fn [raw]
                    (str/split raw #":")) raw-pairs))))

(defn read-passport-db [db]
  (->>
    (str/split db #"\n\n")
    (map read-passport)))

;------ part 1: simple validation
(def expected-fields #{"byr"
                       "iyr"
                       "eyr"
                       "hgt"
                       "hcl"
                       "ecl"
                       "pid"
                       #_"cid"
                       })

(defn validate [passport]
  (set/subset? expected-fields (set (keys passport))))

;------ part 2: deep validation
(defn year-between [from to str-value]
  (<= from (Integer/parseInt str-value) to))

(defn height-between [{:keys [min max unit]} str-value]
  (let [pattern (re-pattern (str "^(\\d+)" unit "$"))]
    (when-let [matched (some->> str-value
                                (re-matches pattern)
                                (second)
                                (Integer/parseInt))]
      (<= min matched max))))

(defn height-valid [str-value]
  (or (height-between {:min 150 :max 193 :unit "cm"} str-value)
      (height-between {:min 59 :max 76 :unit "in"} str-value)))

(defn hair-color-valid [str-value]
  (re-matches #"^#{1}[\da-f]{6}$" str-value))

(defn eye-color-valid [str-value]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} str-value))

(defn pid-valid [str-value]
  (re-matches #"^[\d]{9}$" str-value))

(defn validate-field [testee [k val-fn]]
  (try
    (val-fn (get testee k))
    (catch Exception _e
      false)))

(defn deep-validate [passport]
  (let [validators {"byr" (partial year-between 1920 2002)
                    "iyr" (partial year-between 2010 2020)
                    "eyr" (partial year-between 2020 2030)
                    "hgt" height-valid
                    "hcl" hair-color-valid
                    "ecl" eye-color-valid
                    "pid" pid-valid
                    "cid" (constantly true)}]
    (every? (partial validate-field passport) validators)))

; --------
(defn day4 [_opts]
  (let [db (read-passport-db (slurp "resources/passports.db"))]
    (println (str "Validating passsports. Of " (count db) " passports:\n"
                  "  - " (count (filter true? (map validate db))) " do have the required fields.\n"
                  "  - " (count (filter true? (map deep-validate db))) " fulfill all the requirements.\n"))))

