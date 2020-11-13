(ns day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))
(defn toInt [str] (Integer/parseInt str))
(use 'clojure.data)

(def items (str/split-lines
             (slurp "./src/day3.txt")))
(def parsed-map
  (map
    (fn [x] (str/split x #" ")) items))

(defn vecToInt [vec] (map toInt vec))

(def converted
  (map
    (fn [i] {:key       (get i 0)
             :start-pos (vec (vecToInt
                               (str/split
                                 (get (str/split (get i 2) #":") 0) #",")))
             :range     (vec (vecToInt
                               (str/split (get i 3) #"x")))}) parsed-map))

(defn fill-axis [start-pos ranges key]
  (let [start-x (get start-pos 0)
        start-y (get start-pos 1)]
    (for [x (range (get ranges 0))
          y (range (get ranges 1))
          ]
      {(str (+ start-x x) "," (+ start-y y))
       {:count 1 :pos key :total (* (get ranges 0) (get ranges 1))}})
    ))
(defn conv [p r k] (reduce conj (vec (fill-axis p r k))))



;part3

;120419
(def part-1 (vec (filter
                   (fn [[k {:keys [:count :pos]}]] (< 1 count))
                   (reduce (fn [acc
                                {:keys [:key
                                        :start-pos
                                        :range]}]
                             (merge-with
                               (fn
                                 [x
                                  {:keys
                                   [:pos :count]}]
                                 {:count 2 :pos pos})
                               acc
                               (conv start-pos range key)))

                           {} converted))))
; (= 1 count) === part 2, (< 1 count) === part 1
(count part-1)


(def part-2 (vec (filter
                   (fn [[k {:keys [:count :pos]}]] (< 1 count))
                   (reduce (fn [acc
                                {:keys [:key
                                        :start-pos
                                        :range]}]
                             (merge-with
                               (fn
                                 [x
                                  {:keys
                                   [:pos :count]}]
                                 {:count 2 :pos pos})
                               acc
                               (conv start-pos range key)))

                           {} converted))))
(def new-map (map (fn [[k {:keys [
                                  :pos
                                  :count
                                  :total]}]]
                    {:pos pos :axis k :count count :total total}) part-2))
(def gb (group-by :pos new-map))

(map (fn [[k v]] {k (= (count v) ((get v 0) :total))} gb))
;{"#445" true}

;
;
;(def part1-result (filter
;                    (fn [[k v]] (= 1 v))
;                    (reduce (fn [acc
;                                 {:keys [:key
;                                         :start-pos
;                                         :range]}]
;                              (merge-with + acc (conv start-pos range key)))
;                            ;(reduce conj (vec (fill-axis start-pos range))))
;                            {} converted)))
