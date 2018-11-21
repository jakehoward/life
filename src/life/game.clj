(ns life.game)

;; Rules:
;; 1. Live cell: Fewer than 2 live neighbours => death
;; 2. Live cell: Either 2 or 3 live neighbours => live
;; 3. Live cell: More than 3 live neighbours => death
;; 4. Dead cell: with three live neighbours => live

(def alive 1)
(def dead 0)

(defrecord Point [x y])
(defrecord Format [x y])

(defn index->point [i fmt]
  (->Point (mod i (:x fmt)) (int (/ i (:x fmt)))))

(defn point->value [universe fmt point]
  (nth universe (+ (:x point) (* (:y point) (:x fmt)))))

(defn alive? [universe fmt point]
  (= alive (point->value universe fmt point)))

(defn neighbours [universe fmt point]
  (into #{}
        (for [x [(:x point) (+ (:x point) 1) (- (:x point) 1)]
              y [(:y point) (+ 1 (:y point)) (- (:y point) 1)]
              :when (and (not= (->Point x y) point)
                         (>= x 0)
                         (>= y 0)
                         (< x (:x fmt))
                         (< y (:y fmt)))]
          (->Point x y))))

(defn tick [universe fmt]
  (into []
        (for [idx (range (count universe))]
          (let [point (index->point idx fmt)
                ns (neighbours universe fmt point)
                env (reduce + (map (partial point->value universe fmt) ns))]
            (if (alive? universe fmt point)
              (cond (< env 2) dead
                    (> env 3) dead
                    :else alive)
              (cond (= env 3) alive
                    :else dead))))))

(defn create-universe [fmt]
  (repeatedly (* (:x fmt) (:y fmt)) #(rand-int 2)))
