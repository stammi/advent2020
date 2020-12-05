(ns advent.seating-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [advent.seating :refer :all]))

(deftest findig-a-seat
  (testing "identifying seats ad seat-ids"
    (let [seat-no "FBFBBFFRLR"]
      (is (= [44 5] (seat seat-no)))
      (is (= 357 (seat-id seat-no))))
    (let [seat-no "BFFFBBFRRR"]
      (is (= [70 7] (seat seat-no)))
      (is (= 567 (seat-id seat-no))))
    (let [seat-no "FFFBBBFRRR"]
      (is (= [14 7] (seat seat-no)))
      (is (= 119 (seat-id seat-no))))
    (let [seat-no "BBFFBBFRLL"]
      (is (= [102 4] (seat seat-no)))
      (is (= 820 (seat-id seat-no))))))


