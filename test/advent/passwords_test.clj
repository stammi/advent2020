(ns advent.passwords-test
  (:require [clojure.test :refer :all]
            [advent.passwords :as sut]))



(deftest checking-passwords
  (testing "is valid in the sled shop"
    (is (= true (sut/is-valid sut/sled-valid "1-3 a: abcde")))
    (is (= false (sut/is-valid sut/sled-valid "1-3 b: cdefg")))
    (is (= true (sut/is-valid sut/sled-valid "2-9 c: ccccccccc"))))
  (testing "is valid in the toboggan shop"
    (is (= true (sut/is-valid sut/toboggan-valid "1-3 a: abcde")))
    (is (= false (sut/is-valid sut/toboggan-valid "1-3 b: cdefg")))
    (is (= false (sut/is-valid sut/toboggan-valid "2-9 c: ccccccccc")))))