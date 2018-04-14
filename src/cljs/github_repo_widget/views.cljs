(ns github-repo-widget.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [github-repo-widget.events :as events]
            [github-repo-widget.subs :as subs]
            [github-repo-widget.ui :as ui]))


(defn- readme-tab []
  (let [loading? false
        pane     (ui/component "Tab" "Pane")
        header   (ui/component "Header")]

    [:> pane {:loading loading?}
     "README"]))


(defn- stats-tab []
  (let [loading?   @(re-frame/subscribe [::subs/repo-info-loading?])
        info       @(re-frame/subscribe [::subs/repo-info])
        pane       (ui/component "Tab" "Pane")
        header     (ui/component "Header")
        stat-group (ui/component "Statistic" "Group")
        stat       (ui/component "Statistic")]

    [:> pane {:loading loading?}

     [:> stat-group {:widths 4}

      [:> stat {:label "watchers"
                :value (:watchers_count info)}]

      [:> stat {:label "open issues"
                :value (:open_issues_count info)}]

      [:> stat {:label "forks"
                :value (:forks info)}]

      [:> stat {:label "stargazers"
                :value (:stargazers_count info)}]]]))


(defn- repo-tabs []
  (let [panes [{:menuItem "Readme" :render #(reagent/as-component [readme-tab])}
               {:menuItem "Stats" :render #(reagent/as-component [stats-tab])}]
        tab (ui/component "Tab")]

    [:> tab {:panes panes}]))


(defn- lookup-form []
  (let [grid   (ui/component "Grid")
        col    (ui/component "Grid" "Column")
        form   (ui/component "Form")
        input  (ui/component "Input")
        button (ui/component "Form" "Button")

        repo-name @(re-frame/subscribe [::subs/repo-input-name])

        on-change #(re-frame/dispatch [::events/repo-name-input-changed
                                       (-> % .-target .-value)])
        on-submit #(re-frame/dispatch [::events/fetch-repo])]

    [:> form {:on-submit on-submit}
     [:> grid
      [:> col {:width 12}
       [:> input {:label         "https://github.com/"
                  :placeholder   "kennethkalmer/github-repo-widget"
                  :fluid         true
                  :on-blur       on-change
                  :default-value repo-name}]]

      [:> col {:width 4}
       [:> button {:primary  true
                   :fluid    true
                   :submit   true
                   :on-click on-submit}
        "Fetch"]]]]))


(defn main-panel []
  (let [container (ui/component "Container")
        segment   (ui/component "Segment")
        header    (ui/component "Header")
        info      @(re-frame/subscribe [::subs/repo-info])]

    [:> container
     [:> segment
      [:> header {:as        "h1"
                  :subheader "A sample widget using semantic-ui-react"
                  :content   "GitHub Repo Widget"
                  :icon      "github"}]

      [lookup-form]

      [:div.ui.hidden.divider]

      (when info
        [:> header {:as        "h2"
                    :content   (:name info)
                    :subheader (:description info)}])

      (when info
        [repo-tabs])]]))
