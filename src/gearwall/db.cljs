(ns gearwall.db)

(def default-db
  {:name "re-frame"
   :gears {:driver {:name :driver
                    :tooth-count 12}
           :follower {:name :follower
                      :tooth-count 8}}})
