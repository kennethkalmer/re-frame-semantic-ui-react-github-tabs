(ns github-repo-widget.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [github-repo-widget.db :as db]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))


(re-frame/reg-event-db
 ::repo-name-input-changed
 (fn-traced [db [_ val]]
   (assoc db :repo-name-input val)))


(re-frame/reg-event-fx
 ::fetch-repo
 (fn-traced
  [{db :db} _]
  (let [repo     (:repo-name-input db)
        base-uri (str "https://api.github.com/repos/" repo)]

    {:db         (-> db
                     (assoc-in [:loading :info] true))
     :http-xhrio [{:method          :get
                   :uri             base-uri
                   :timeout         5000
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success      [::info-loaded]
                   :on-failure      [::info-failed]}]})))


(re-frame/reg-event-db
 ::info-loaded
 (fn-traced
  [db [_ repo-info]]
  (-> db
      (assoc-in [:loading :info] false)
      (assoc :repo-info repo-info))))


(re-frame/reg-event-db
 ::info-failed
 (fn-traced
  [db [_ xhrio]]
  (-> db
      (assoc-in [:errors :info] xhrio)
      (assoc-in [:loading :info] false))))
