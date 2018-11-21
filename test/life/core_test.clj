(ns life.core-test
  (:require [clojure.test :refer :all]
            [life.game :refer :all]))

(def universe
  [1 0 0 1
   0 1 1 0
   1 1 1 1
   0 0 0 0])

(def fmt (->Format 4 4))

(def surrounding-square-cases
  [{:point (->Point 0 0) :neighbours #{(->Point 1 0) (->Point 1 1) (->Point 0 1)}}
   {:point (->Point 3 0) :neighbours #{(->Point 3 1) (->Point 2 1) (->Point 2 0)}}
   {:point (->Point 0 3) :neighbours #{(->Point 0 2) (->Point 1 2) (->Point 1 3)}}
   {:point (->Point 3 3) :neighbours #{(->Point 2 2) (->Point 3 2) (->Point 2 3)}}
   {:point (->Point 1 1) :neighbours #{(->Point 0 0) (->Point 1 0) (->Point 2 0)
                                       (->Point 2 1) (->Point 2 2) (->Point 1 2)
                                       (->Point 0 2) (->Point 0 1)}}
   {:point (->Point 3 2) :neighbours #{(->Point 2 1) (->Point 3 1) (->Point 3 3)
                                       (->Point 2 3) (->Point 2 2)}}])

(deftest game
  (testing "a tick"
    (is (= (tick universe fmt)
           [0 1 1 0
            0 0 0 0
            1 0 0 1
            0 1 1 0]))))

(deftest utility-functions
  (testing "finds correct surrounding squares"
    (doseq [test-case surrounding-square-cases]
      (is (= (:neighbours test-case)
             (neighbours universe fmt (:point test-case)))))))
