(ns gearwall.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [gearwall.subs :as subs]
   [clojure.string :as string]
   ))

(defonce radians-conversion-factor (/ Math/PI 180))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :src   (at)
     :label (str @name)
     :level :level1]))

(defn motion-panel []
  (let [motion (re-frame/subscribe [:motion/motion 5 10])]
    [re-com/v-box
     :src    (at)
     :height  "100%"
     :children [[:p (str @motion)]]]))

(defn polar-to-cartesian [r theta]
  (let [
        x (* r (Math/cos (* radians-conversion-factor theta)))
        y (* r (Math/sin (* radians-conversion-factor theta)))]
    (list x y)))

(defn circle [r]
  (let [function (fn [theta] (list r theta))
        points (map function (range 0 360))]
    (mapcat  #(apply polar-to-cartesian %) points)))

(defn gear [r]
  (let [teeth (map (comp (partial + r r r)
                         (partial * r)
                         Math/cos
                         (partial * 6 radians-conversion-factor)) (iterate inc 1))
        angles (iterate inc 1)
        points (take 361 (partition 2 (interleave teeth angles)))]
    (mapcat #(apply polar-to-cartesian %) points)
    )
  )

(defn gear-panel []
  (let [paths [(gear 60)
               (gear 80)]]
    [:svg
     {:style {:width 800
              :height 800}
      :view-box "-400 -400 800 800"}
     (for [[x y & more-points] paths]
       [:path {:fill "none"
               :stroke "black"
               :d (str "M " x " " y " L " (string/join " " more-points))}])

     ]))



(defn main-panel []
  [re-com/v-box
   :src      (at)
   :height   "100%"
   :children [[title]
              #_[motion-panel]
              [gear-panel]
              ]])
