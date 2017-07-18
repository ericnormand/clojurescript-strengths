(ns strengths.events
  (:require [re-frame.core :as re-frame]
            [strengths.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-name
 (fn [db [_ first-name last-name]]
   (assoc db :first-name first-name :last-name last-name)))
