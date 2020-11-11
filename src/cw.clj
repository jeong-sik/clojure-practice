;code-wars

(ns cw
  (:require [clojure.string :as str]))
(def test-case1 "a       b    ");
(defn upper-case [c] (str/upper-case c))

(map (for [x [test-case1]
           :let [y (upper-case x)]]
       y) test-case1)

(defn make-upper-case [n]
  (loop (count n)))
(require 'clojure.string)

(defn toUpper [words p] (str/capitalize (get words p)))


(def test-word " gap ")

(take (count test-word) )

(reduce (fn [a, v] (str/upper-case v) )test-word)

(let [split-word (str/split test-word #"")
      ]
  (println split-word))



(defn tr [^CharSequence s i] (let [s (.toString s)])
  (str
    (.toLowerCase (subs s 0 i) )
    (.toUpperCase (subs s i (+ i 1)) )
    (.toLowerCase (subs s (+ i 1) (- (count s) 0)) )
    ))

(defn t [s] (map-indexed
              (fn [index, c]
                (tr c index)
                )
              (for [x (range (count s))] s)))

(defn lowerCaseFilter [cmp1] (= cmp1 (str/lower-case cmp1)))

(defn wave [string] (vec ( remove (fn [x] (= x (str/lower-case x))) (vec (set (t string))))))

(wave "okoa ")
(sort (wave " gap "))
(filter
  (fn [x]
    #(not (
           (= x (str/lower-case x)
              )))) (vec (set (t " a   b   "))))


(defn t2 [x] (= x (str/lower-case x)))



(defn trans [x] (str (.toLowerCase(subs 0 (1)))
                     (.toUpperCase(subs 0 x 2))
                     (.toLowerCase(subs 0 3 ))))



(= "ab" (str/lower-case "Ab"))




(def v [1 2 3 4 5 6 7 8 9 0])

(defn toTelephone [s] (let [a (str/join (subvec s 0 3))
                            b (str/join (subvec s 3 6))
                            c (str/join (subvec s 6 10))
                            ] (str (str "(" a ")") " " b "-" c)))

(apply format "(%d%d%d) %d%d%d-%d%d%d%d" v)

(toTelephone v)

(map (partial format "%d %s") (iterate inc 1)["a" "b"])


(def words "oh my god")
(def filterChars ["a" "e" "o" "i" "u"])

(clojure.string/join (filter
                       #(not (some (fn [u] (= u %))
                                   filterChars))
                       (clojure.string/split words #"") )   )


(def filterChars ["a" "e" "o" "i" "u"])

(map clojure.string/upper-case filterChars)

(defn disemvowel [string]
  (clojure.string/join
    (filter
      #(not (some (fn [u] (= u %))
                  filterChars))
      (clojure.string/split string #"") )) )


(disemvowel "asdasdsad")
(map #(second %) {1 2, 2 1})

(def test1 [20 1 -1 2 -2 3 3 5 5 1 2 4 20 4 -1 -2 5])
(println test1)

(defn find-odd [xs]
  (ffirst (filter #(odd? (second %)) (frequencies xs)))
  )

(find-odd [1 2])

(defn odd-both [x y] (and (odd? x) (odd? y)))
(odd-both 2 4)

(def a [5 3 2 8 1 4])

(sort (fn [left, right] (println left right) ) a)

(reduce (fn [left right] (if (odd-both left right) (+ left right) (reduced? [1]))) test1)
(reduce (fn [a v] (if (< a 100) (+ a v) (reduced   :big))) (range 10))

