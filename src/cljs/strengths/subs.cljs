(ns strengths.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (when (contains? db :first-name)
     {:first-name (:first-name db)
      :last-name  (:last-name  db)})))
