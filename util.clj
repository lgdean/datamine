;; general-purpose math functions

(defn square [x] (* x x))

;;; sometimes we care about vectors

(defn dot2 [x1 y1 x2 y2] (+ (* x1 x2) (* y1 y2)))

(defn dot [v1 v2]
  (assert (= (count v1) (count v2)) (format "mismatch: %s %s" v1 v2))
  (reduce + (map * v1 v2)))

(defn mag [ds] (Math/sqrt (reduce + (map square ds))))

(defn cos [v1 v2]
  (/ (dot v1 v2)
     (* (mag v1) (mag v2))))

(defn add-vecs [& vs]
  (assert (apply = (map count vs))) ; hm, this line looks familiar
  (apply mapv + vs))

;;; ... and matrices!

(defn assert-rectangular [m]
  (assert (apply = (map count m)) (format "row lengths differ: %s" m)))

(defn vec-mult [v cols]
  (mapv (partial dot v) cols))

(defn mult-vec [rows v] ; v is a column vector, by the way
  (mapv (partial dot v) rows))

(defn mult-t [rows cols]
  (mapv (partial mult-vec rows) cols))

(defn transpose [rows]
  (apply mapv vector rows))

(defn mult [m1 m2]
  (assert-rectangular m1)
  (assert-rectangular m2)
  (mult-t m1 (transpose m2)))


;;; statistics related to clustering

;; variance may mean different things in different contexts.
(defn variance [n s ssq] (- (/ ssq n) (square (/ s n))))

(defn mahalanobis [point n sums sumsqs]
  ;; assumes point, sums, and sumsqs are all the same length!
  (let [centroid (map #(/ % n) sums)
        dists (map - point centroid) ; (could be negative)
        variances (map (partial variance n) sums sumsqs)]
    (Math/sqrt (reduce + (map / (map square dists) variances)))))

;; and other geometry

(defn dist-squared [p1 p2]
  (reduce + (map square (map - p1 p2))))

(defn closest [p centroids]
  (apply min-key first (map #(vector (dist-squared p %) %) centroids)))


;;; misc probability math

(defn or-magic [p n] (- 1 (Math/pow (- 1 p) n)))

(defn not-any [& ps] (reduce * (map #(- 1 %) ps)))
(defn prob-or [& ps] (- 1 (apply not-any ps)))
(defn or-magic2 [p n] (apply prob-or (repeat n p)))
;; that "apply" noise surely can't be idiomatic...
