(ns strengths.external
  (:require
   [reagent.core :as reagent]
   [cljsjs.react-chartjs-2]))

(def data #js { :labels #js ["January", "February", "March", "April", "May", "June", "July"],
               :datasets #js [#js {:label "React Chart.js 2",
                                   :backgroundColor "rgba(255,99,132,0.2)",
                                   :borderColor "rgba(255,99,132,1)",
                                   :borderWidth 1,
                                   :hoverBackgroundColor "rgba(255,99,132,0.4)",
                                   :hoverBorderColor "rgba(255,99,132,1)",
                                   :data #js [65, 59, 80, 81, 56, 55, 40]}]})

(defn horizontal-bar []
  [:> js/reactChartjs2.HorizontalBar {:data data}])
