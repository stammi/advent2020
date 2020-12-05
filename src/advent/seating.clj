(ns advent.seating
  (:require [clojure.string :as str]))

(defn find-no [interval up down input]
  (first (reduce (fn [[min max] c]
                   (let [step (+ 1 (quot (- max min) 2))]
                     (cond (= c up) [min (- max step)]
                           (= c down) [(+ min step) max])))
                 interval input)))

(defn seat [seat-no]
  [(find-no [0 127] \F \B (subs seat-no 0 7))
   (find-no [0 7] \L \R (subs seat-no 7))])

(defn seat-id [seat-no]
  (let [[row col] (seat seat-no)]
    (+ (* row 8) col)))

(defn find-first-missing [seat-ids]
  (->> seat-ids
       sort
       (partition 2)
       (keep (fn [[s1 s2]]
               (let [expected (+ s1 1)]
                 (when (not (= s2 expected))
                   expected))))
       first))

(defn day5 [_opts]
         (let [seat-ids (map seat-id (str/split-lines (slurp "resources/seats.db")))]
           (println (str "All boarding passes scanned.\n"
                         "Highest seat number is " (reduce max seat-ids) ".\n"
                         (find-first-missing seat-ids) " appears to be my seat.\n"))))