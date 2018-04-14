(ns github-repo-widget.views
  (:require [re-frame.core :as re-frame]
            [github-repo-widget.subs :as subs]
            [github-repo-widget.ui :as ui]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div "Hello from " @name]))
