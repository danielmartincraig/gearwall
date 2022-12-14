(ns gearwall.subs
  (:require
   [re-frame.core :as re-frame]
   [gearwall.gear :as gear]))

(defn take-range [coll start stop]
  (take stop (drop start coll)))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::gears
 :-> :gears)

(re-frame/reg-sub
 :gears/driver
 :<- [::gears]
 (fn [gears query-vec]
   (:driver gears)))

(re-frame/reg-sub
 :gears/follower
 :<- [::gears]
 (fn [gears query-vec]
   (:follower gears)))

(re-frame/reg-sub
 :motion/motion
 :<- [:gears/driver]
 :<- [:gears/follower]
 (fn [[{driver-name :name driver-tooth-count :tooth-count driver-speed :speed :as driver}
       {follower-name :name follower-tooth-count :tooth-count :as follower}] [_ start-time stop-time]]
   (let [driver-motion (map (partial * driver-speed) (iterate inc 1))
         follower-motion (map (partial * (/ driver-tooth-count follower-tooth-count)) driver-motion)
         motion (partition 2 (interleave driver-motion follower-motion))]
     (take-range motion start-time stop-time)
     )))

(re-frame/reg-sub
 :gears/driver-path
 :<- [:gears/driver]
 (fn [{:keys [radius tooth-count x y]} query-vec]
   (gear/gear radius 0 tooth-count x y)))

(re-frame/reg-sub
 :gears/follower-path
 :<- [:gears/follower]
 (fn [{:keys [radius tooth-count x y]} query-vec]
   (gear/gear radius 0 tooth-count x y)))

(re-frame/reg-sub
 :gears/paths
 :<- [:gears/driver-path]
 :<- [:gears/follower-path]
 (fn [paths query-vec]
   paths))

(comment

  @(re-frame/subscribe [::gears])
  @(re-frame/subscribe [:gears/driver])
  @(re-frame/subscribe [:gears/follower])
  @(re-frame/subscribe [:motion/motion 5 20])
  @(re-frame/subscribe [:gears/driver-path])

  )
