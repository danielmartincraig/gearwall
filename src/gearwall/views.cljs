(ns gearwall.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [gearwall.subs :as subs]
   [gearwall.gear :as gear]
   [clojure.string :as string]
   ))

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

(defn gear-panel []
  (let [paths (re-frame/subscribe [:gears/paths])]
    [:svg
     {:style {:width 800
              :height 800}
      :view-box "-400 -400 800 800"}
     (for [[x y & more-points] @paths]
       [:path {:fill "maroon"
               :stroke "white"
               :d (str "M " x " " y " L " (string/join " " more-points))}])]))

(defn main-panel []
  [re-com/v-box
   :src      (at)
   :height   "100%"
   :children [[title]
              #_[motion-panel]
              [gear-panel]
              ]])
