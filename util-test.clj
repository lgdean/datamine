(ns yay-tests
  (:require [clojure.test :refer :all])
  (load-file "util.clj"))

(deftest vector-addition-test
  (testing "vector addition"
    (is (= [4 6] (add-vecs [1 2] [3 4])))))

(deftest matrix-multiplication-test
  (testing "matrix multiplication" ; as the name suggests
    (is (= [[ 0 -6]
            [-5 -7]]
           (mult [[1  0 -2]
                  [0  3 -1]]
                 [[ 0  3]
                  [-2 -1]
                  [ 0  4]])))))

(deftest geometry-test
  (testing "closest point (with distance squared)"
    (is (= [4 [4 8]]
           (closest [6 8] '([4 10] [4 8] [7 10]))))))

(run-tests)
