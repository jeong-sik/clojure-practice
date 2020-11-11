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

(defn fill-axis [start-pos ranges]
  (let [start-x (get start-pos 0)
        start-y (get start-pos 1)]
    (for [x (range (get ranges 0))
          y (range (get ranges 1))
          ]
      {(str (+ start-x x) "," (+ start-y y)) 1})
    ))
;(reduce conj (vec (fill-axis [200 200] [2 2])))

(defn conv [p r] (reduce conj (vec (fill-axis p r))))
;(conv [200 200] [2 2])

;part3
(count (filter
         (fn [[k v]] (< 1 v))
         (reduce (fn [acc
                      {:keys [:key
                              :start-pos
                              :range]}]
                   (merge-with + acc (conv start-pos range)))
                 ;(reduce conj (vec (fill-axis start-pos range))))
                 {} converted)))
;120419

;(reduce
;  (fn [acc {:keys [:value :key]}] value)
;  [{:key "a" :value 1}, {:key "b" :value 2}]
;  )