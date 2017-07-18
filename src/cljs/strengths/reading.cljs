(ns strengths.reading
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [cljs.reader]))

(def value (cljs.reader/read-string "[1 2 3 4 5]"))

(defn user-info-wizard [on-finish]
  (let [step (reagent/atom 1)
        data (reagent/atom {})]
    (fn [callback]
      (case @step
        1
        [:form {:on-submit (fn [e]
                             (.preventDefault e)
                             (swap! step inc))}
         [:input {:on-change #(swap! data
                                     assoc :first-name (-> % .-target .-value))
                  :placeholder "First Name"
                  :type :text
                  :value (:first-name @data)}]]
        2
        [:form {:on-submit (fn [e]
                             (.preventDefault e)
                             (swap! step inc))}
         [:input {:on-change #(swap! data
                                     assoc :last-name (-> % .-target .-value))
                  :placeholder "Last Name"
                  :type :text
                  :value (:last-name @data)}]]
        3
        [:form {:on-submit (fn [e]
                             (.preventDefault e)
                             (swap! step inc))}
         [:input {:on-change #(swap! data
                                     assoc :age (-> % .-target .-value))
                  :placeholder "Age"
                  :type :text
                  :value (:age @data)}]]
        4
        [:form {:on-submit (fn [e]
                             (.preventDefault e)
                             (swap! step inc)
                             (on-finish @data))}
         [:input {:on-change #(swap! data
                                     assoc :city (-> % .-target .-value))
                  :placeholder "City"
                  :type :text
                  :value (:city @data)}]]
        [:div "Done!"]))))

(def wizard [:wizard
             [:input :first-name "First Name"]
             [:input :last-name "Last Name"]
             [:input :age "Age"]
             [:input :city "City"]])

(defn intr [step data exp on-finish]
  (case (first exp)
    :wizard
    (let [i @step
          steps (vec (rest exp))]
      (if (and (int? i) (>= i 0) (< i (count steps)))
        (intr step data (get steps i) (fn []
                                        (when (>= i (count steps))
                                          (on-finish @data))))
        [:div "Done!"]))
    :input
    (let [[_ k ph] exp]
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (swap! step inc)
                           (on-finish))}
       [:input {:on-change #(swap! data
                                   assoc k (-> % .-target .-value))
                :placeholder ph
                :type :text
                :value (get @data k)}]])))

(defn interpreter-element [exp on-finish]
  (let [step (reagent/atom 0)
        data (reagent/atom {})]
    (fn [exp]
      (intr step data exp on-finish))))
