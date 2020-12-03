(ns advent.toboggan-ride-test
  (:require [clojure.test :refer :all])
  (:require [advent.toboggan-ride :as sut]))

(def test-terrain ["..##......."
                   "#...#...#.."
                   ".#....#..#."
                   "..#.#...#.#"
                   ".#...##..#."
                   "..#.##....."
                   ".#.#.#....#"
                   ".#........#"
                   "#.##...#..."
                   "#...##....#"
                   ".#..#...#.#"])

(def testworld-1-3 (sut/start test-terrain
                              [1 3]))

(deftest tree?-test
  (testing "finding out wether or not there is a tree"
    (is (= true (sut/tree? (assoc testworld-1-3 :position [1 0]))))
    (is (= false (sut/tree? (assoc testworld-1-3 :position [1 1])))))
  (testing "finding out wether or not there is a tree when on the next topology repetition"
    (is (= true (sut/tree? (assoc testworld-1-3 :position [1 11]))))
    (is (= true (sut/tree? (assoc testworld-1-3 :position [12 0]))))))

(deftest wheeeeeee!-test
  (testing "movement right down"
    (is (= [[0 0] [1 3] [2 6]] (take 3 (map :position (sut/wheeeeee! testworld-1-3))))))
  (testing "takes a certai number of steps in the example"
    (is (= 11 (count (sut/wheeeeee! testworld-1-3)))))
  (testing "movement ends when at the bottom"
    (is (= [10 30] (:position (last (take 22 (sut/wheeeeee! testworld-1-3))))))))

(deftest counting-trees
  (testing "there is 7 trees in the slide on the test terrain"
    (is (= 7 (count (filter sut/tree? (sut/wheeeeee! testworld-1-3)))))))