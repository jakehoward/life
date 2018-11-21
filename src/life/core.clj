(ns life.core
  (:require [life.game :refer :all])
  (:import (java.awt Color Dimension)
           (java.awt.event ActionListener)
           (javax.swing JPanel JFrame JOptionPane Timer))
  (:gen-class))

(def fmt (->Format 50 50))
(def the-universe (atom (create-universe fmt)))

(def point-size 10)
(def generation-millis 200)

(defn point-to-screen-rect [point]
  (map #(* point-size %)
       [(:x point) (:y point) 1 1]))

(defn fill-point [g point color]
  (let [[x y width height] (point-to-screen-rect point)]
    (.setColor g color)
    (.fillRect g x y width height)))

(defn paint [g universe fmt]
  (doseq [idx (range (count universe))]
    (let [point (index->point idx fmt)
          color (if (alive? universe fmt point) (Color. 0 0 0) (Color. 220 220 220))]
      (fill-point g point color))))

(defn game-panel [frame]
  (proxy [JPanel ActionListener] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (paint g @the-universe fmt))
    (actionPerformed [e]
      (let [prev-universe @the-universe
            next-universe (tick @the-universe fmt)]
        (when (= prev-universe next-universe)
          (JOptionPane/showMessageDialog frame "The universe is in equilibrium!")
          (System/exit 0))
        (reset! the-universe next-universe)
        (.repaint this)))
    (getPreferredSize []
      (Dimension. (* (inc (:x fmt)) point-size)
                  (* (inc (:y fmt)) point-size)))))

(defn game []
  (let [frame (JFrame. "Game of Life")
        panel (game-panel frame)
        timer (Timer. generation-millis panel)]
    (doto panel
      (.setFocusable true))
    (doto frame
      (.add panel)
      (.pack)
      (.setVisible true))
    (.start timer)
    [timer]))

(defn -main
  "Conway's Game of Life"
  [& args]
  (game))
