(ns day4
  (:require [clojure.string :as str])
  (:require [clojure.instant :as instant]))

(defn toInt [str] (Integer/parseInt str))
(use 'clojure.data)

(def items (sort (str/split-lines
                   (slurp "./src/day4.txt"))))
(count items)
;(/ (-
;     (inst-ms (instant/read-instant-date "1518-11-23T00:13"))
;     (inst-ms (instant/read-instant-date "1518-11-23T00:03"))) 60000)

(defn convert-date-time-format [s] (str/replace s #" " "T"))
(defn get-string-time [s] (subs s 1 17))

(defn get-ms [s] (inst-ms (instant/read-instant-date s)))
(->> "[1518-11-22 00:59] wakes up"
     get-string-time
     convert-date-time-format
     get-ms)

(defn get-diff-min [after before]
  (if (or (nil? after) (nil? before))
    after
    (/ (- (->> after
               get-string-time
               convert-date-time-format
               get-ms)
          (->> before
               get-string-time
               convert-date-time-format
               get-ms)) 60000))
  )

(get-diff-min nil "[1518-11-23 00:03] Guard #3019 begins shift")
(defn get-guard-id [s] (re-find #"\#\d+" s))

(def partition-seq (vec (partition 2 1 items)))

(def time-diffs (map (fn [x]
                       (get-diff-min (get (vec x) 1) (get (vec x) 0))
                       ) partition-seq))

(def time-diffs2 (conj time-diffs 0))

(def sorted (sort (zipmap (vec items) (vec time-diffs2))))

(def onlys (filter
             (fn [x]
               (or
                 (str/includes? (get x 0) "wakes up")
                 (str/includes? (get x 0) "Guard #")))
             sorted))

(defn make-array [start end]
  count (flatten (conj (vec (take start (repeat 0)))
                       (vec (take (- end start) (repeat 1)))
                       (vec (take (- 60 end) (repeat 0))))))

(def with-array (map (fn [[k v]]
                       (let [msg k
                             hour (toInt (subs msg 12 14))
                             min (toInt (subs msg 15 17))
                             start (- min v)
                             end min]
                         [k v min [(- min v) min] (vec (make-array start end))]
                         )
                       ) onlys))

(def arr (map
           (fn [x]
             (let [id (get-guard-id (get x 0))
                   min (get x 1)
                   array (last x)
                   ]
               (if (nil? id)
                 array
                 id
                 )
               )
             )
           with-array))

(def sa (str/split (str/join " " arr) #" #"))

(def sa2 (vec (map (fn [x] (str/replace x "#" "")) sa)))

(defn split-items
  [item]
  (map (fn [x] (str/replace x #"]" "")) (str/split item #" \[")))


(def m (map split-items sa2))

(def kv (map (fn [x] {:key  (get (vec x) 0)
                      :vals (vec (rest (vec x)))}) m))

(def my-r (map
            (fn
              [{:keys [:key :vals]}]
              {:key key :vals (vec (map (fn [x]
                                          (map toInt (str/split x #" "))) (vec vals)))}
              ) kv))


(def result-key-values (reduce (fn [acc {:keys
                                         [:key
                                          :vals]}] (assoc acc key (conj (get acc key) vals))) {} my-r))

;part-1
(defn get-sum [v]
  (if (nil? v) nil
               (vec (reduce (fn [acc1 y] (map + acc1 y)) (reduce (fn [acc x]
                                                                   (into acc x)
                                                                   ) v)))))

(def m-k (vec (keys result-key-values)))
(def final-result (reduce (fn [acc x]
                            (if
                              (not
                                (= 0 (count (flatten (get result-key-values x)))))
                              (assoc acc x {:sum   (reduce + (get-sum (get result-key-values x)))
                                            :max   (reduce max (get-sum (get result-key-values x)))
                                            :array (get-sum (get result-key-values x))}
                                         ) acc
                              )
                            ) {} m-k))

;part-2 ;1153 * 32(pos 17) = 36896
(get final-result "1153")

;part-1 421 * 27(pos 15) = 11367
(get final-result "421")
