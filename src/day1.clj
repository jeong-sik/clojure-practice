(ns day1

  (:require [clojure.string :as str]))
(defn toInt [str] (Integer/parseInt str))
(def items (map toInt
                (str/split-lines
                  (slurp "./src/day1.txt"))))
(reduce + items);
;547

(defn check [{:keys [:sum :seen]} el]
        (let [new-sum (+ el sum)
              new-seen (conj seen new-sum)]
          (if (seen new-sum)
            (reduced new-sum)
            {:sum new-sum :seen new-seen})))

(reduce check
    {:sum 0 :seen #{}}
        (cycle items))
;76414
