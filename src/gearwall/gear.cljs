(ns gearwall.gear
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [clojure.string :as string]
   ))

(defonce radians-conversion-factor (/ Math/PI 180))

(defn polar-to-cartesian [r theta]
  (let [x (* r (Math/cos (* radians-conversion-factor theta)))
        y (* r (Math/sin (* radians-conversion-factor theta)))]
    (list x y)))

(defn gear [r theta tooth-count x y]
  (let [teeth (map (comp (partial + r r)
                         (partial * 0.5 r)
                         Math/cos
                         (partial + theta)
                         (partial * tooth-count radians-conversion-factor)) (iterate inc 1))
        angles (iterate inc 1)
        points (take 361 (partition 2 (interleave teeth angles)))]
    (mapcat (comp (partial map + [x y])
                  (partial apply polar-to-cartesian)) points)))
