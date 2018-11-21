(defproject life "1.0.0"
  :description "Conway's Game of Life"
  :url "http://github.com/jakehoward/life"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot life.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
