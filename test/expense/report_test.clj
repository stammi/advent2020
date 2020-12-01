(ns expense.report-test
  (:require [clojure.test :refer :all])
  (:require [expense.report :as sut]))

(deftest day1-test
  (testing "The examples from https://adventofcode.com/2020/day/1"
    (let [input [1721 979 366 299 675 1456]]
      (is (= 514579 (last (sut/day1-part1 input))))
      (is (= 241861950 (last (sut/day1-part2 input)))))))
