(ns advent.toboggan-ride
  (:require [clojure.string :as str]))

(defn tree? [{:keys [terrain rows cols position]}]
  (let [[x y] position]
    (= \# (-> terrain
              (get (mod x rows))
              (get (mod y cols))))))

(defn start [terrain speed]
  {:terrain  terrain
   :position [0 0]
   :speed    speed
   :rows     (count terrain)
   :cols     (count (first terrain))})

(defn all-the-way? [{:keys [rows position]}]
  (= (dec rows) (first position)))

(defn slide [{:keys [position speed] :as world}]
  (let [[x y] position
        [v-x v-y] speed]
    (assoc world :position [(+ v-x x) (+ v-y y)])))

(defn wheeeeee! [state]
  (lazy-seq
    (let [new-state (slide state)]
      (cons state (if (all-the-way? new-state)
                    [new-state]
                    (wheeeeee! new-state))))))

(defn slide-n-count [terrain slope]
  (let [world (start terrain slope)]
    (count (filter tree? (wheeeeee! world)))))

(defn day3 [_opts]
  (let [terrain (str/split-lines (slurp "resources/terrain.map"))
        slopes [[1 1] [1 3] [1 5] [1 7] [2 1]]]
    (println (str "Slipping all the slopes we saw a product of "
                  (reduce * (map (partial slide-n-count terrain) slopes))
                  " trees."))))