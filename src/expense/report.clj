(ns expense.report
  (:require [expense.input :as i]
            [clojure.string :as str]))

(defn day1-part1
  "product of two numbers that add to 2020"
  [numbers]
  (let [zipped (into [] (zipmap (range) numbers))]
    (first (for [[i1 n1] zipped
                 [i2 n2] zipped :when (and (< i1 i2) (= 2020 (+ n1 n2)))]
             [[n1 n2] (* n1 n2)]))))

(defn day1-part2
  "product of three numbers that add to 2020"
  [numbers]
  (let [zipped (into [] (zipmap (range) numbers))]
    (first (for [[i1 n1] zipped
                 [i2 n2] zipped
                 [i3 n3] zipped :when (and (< i1 i2 i3) (= 2020 (+ n1 n2 n3)))]
             [[n1 n2 n3] (* n1 n2 n3)]))))

(defn result-to-str [[[& ns] result]]
  (str (str/join ", " (drop-last ns)) " & " (last ns)
       " add up to '" (reduce + ns) "' and their product is '" result "'.\n") )

(defn day1 [opts]
  (let [input i/input-day1]
    (println (str "Here is the expense report for day 1:\n\n"
                  (result-to-str (day1-part1 input))
                  (result-to-str (day1-part2 input))))))

