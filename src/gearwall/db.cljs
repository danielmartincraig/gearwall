(ns gearwall.db)

(def default-db
  {:name "gearwall"
   :gears {:driver {:name :driver
                    :speed 1
                    :tooth-count 12}
           :follower {:name :follower
                      :tooth-count 8}}})
