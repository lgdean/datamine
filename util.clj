(defn square [x] (* x x))

(defn dot2 [x1 y1 x2 y2] (+ (* x1 x2) (* y1 y2)))

(defn dot [v1 v2] (reduce + (map * v1 v2)))

(defn mag [ds] (Math/sqrt (reduce + (map square ds))))

(defn cos [v1 v2]
  (/ (dot v1 v2)
     (* (mag v1) (mag v2))))

;; variance may mean different things in different contexts.
(defn variance [n s ssq] (- (/ ssq n) (square (/ s n))))

(defn mahalanobis [point n sums sumsqs]
  ;; assumes point, sums, and sumsqs are all the same length!
  (let [centroid (map #(/ % n) sums)
        dists (map - point centroid) ; (could be negative)
        variances (map (partial variance n) sums sumsqs)]
    (Math/sqrt (reduce + (map / (map square dists) variances)))))

(defn or-magic [p n] (- 1 (Math/pow (- 1 p) n)))

(defn not-any [& ps] (reduce * (map #(- 1 %) ps)))
(defn prob-or [& ps] (- 1 (apply not-any ps)))
(defn or-magic2 [p n] (apply prob-or (repeat n p)))
;; that "apply" noise surely can't be idiomatic...

