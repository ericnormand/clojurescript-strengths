(ns strengths.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [strengths.external :as ext]
   [strengths.reading :as reading]))

(defn user-card [first-name last-name]
  [:div
   [:div first-name]
   [:div last-name]])

(defn user-form []
  (let [s (reagent/atom {})]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (re-frame/dispatch [:set-name
                                               (:first-name @s)
                                               (:last-name @s)]))}
       [:input {:type :text
                :placeholder "First Name"
                :value (:first-name @s)
                :on-change #(swap! s
                                   assoc :first-name (-> % .-target .-value))}]
       [:input {:type :text
                :placeholder "Last Name"
                :value (:last-name @s)
                :on-change #(swap! s
                                   assoc :last-name (-> % .-target .-value))}]
       [:button {:type :submit} "Save"]])))

(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       (if (some? @name)
         [user-card (:first-name @name) (:last-name @name)]
         [user-form])
       [reading/user-info-wizard #(js/console.log (pr-str %))]
       [reading/interpreter-element reading/wizard
        #(js/console.log (pr-str %))]])))
