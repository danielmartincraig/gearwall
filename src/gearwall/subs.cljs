(ns gearwall.subs
  (:require
   [re-frame.core :as re-frame]))

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
 (fn [[{driver-name :name driver-tooth-count :tooth-count :as driver}
       {follower-name :name follower-tooth-count :tooth-count :as follower}] query-vec]
   (let [driver-motion (iterate inc 1)
         follower-motion (map (partial * (/ driver-tooth-count follower-tooth-count)) driver-motion)]
     {driver-name driver-motion
      follower-name follower-motion})))

(comment

  @(re-frame/subscribe [::gears])
  @(re-frame/subscribe [:gears/driver])
  @(re-frame/subscribe [:gears/follower])
  (take 10 (:driver @(re-frame/subscribe [:motion/motion])))
  (take 10 (:follower @(re-frame/subscribe [:motion/motion])))

  )
