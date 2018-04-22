(ns github-repo-widget.subs
  (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
 ::repo-input-name
 (fn [db]
   (:repo-name-input db)))

(re-frame/reg-sub
 ::repo-info
 (fn [db]
   (:repo-info db)))


(re-frame/reg-sub
 ::repo-info-loading?
 (fn [db]
   (get-in db [:loading :info])))


(re-frame/reg-sub
 ::repo-readme
 (fn [db]
   (:repo-readme db)))


(re-frame/reg-sub
 ::repo-readme-loading?
 (fn [db]
   (get-in db [:loading :readme])))
