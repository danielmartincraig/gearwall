(ns gearwall.db)

(def default-db
  {:name "gearwall"
   :gears {:driver {:name :driver
                    :radius 100
                    :speed 1
                    :tooth-count 12
                    :x -100
                    :y 0}
           :follower {:name :follower
                      :radius 50
                      :tooth-count 8
                      :x 220
                      :y 0}}})
