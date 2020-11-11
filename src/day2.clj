(ns day2
  (:require [clojure.string :as str]
            [clojure.set :as set]))
(use 'clojure.data)

(def items (str/split-lines (slurp "./src/day2.txt")))
(def v-items (map vec items))
(map set items)
(map (fn [item] (count item)) items)

(map str items)
(defn inspect [v] (reduce (fn [acc k]
               (if (contains? acc k)
                 (assoc acc k (+ (get acc k) 1))
                 (assoc acc k 1)
                 ))
             {}
        v))
;same frequencies fn

(count (filter (fn [[k v]] (= v 2)) (filter (fn [[k v]] (< 1 v)) (mapcat inspect items))))
(count (filter (fn [[k v]] (= v 3)) (filter (fn [[k v]] (< 1 v)) (mapcat inspect items))))


(defn cf [input] (filter (fn [[_ v]] (= 2 v) ) input))
;3 or 2
(count
  (filter
    (fn [x] (< 0 (count x))) (map cf (map inspect items))))


;part 1
;(* 247 39) = 9633
(* 247 39)




;part 2
(defn diff-result [x y] (last (diff x y)))

(def a (reduce
         (fn [acc x]
           (into acc (map (fn [y] (remove nil? (diff-result y x))) v-items))
           ) v-items))

(def b (remove nil? a))
(filter (fn [c] (= 25 (count c) ))b)

(str/join
  (get
    (vec
      (set
        (filter
          (fn [x] (= 25 (count x)))
          (filter seq? b)))) 0))
;result
;lujnogabetpmsydyfcovzixaw


