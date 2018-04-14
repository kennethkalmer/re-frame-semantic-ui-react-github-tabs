(ns github-repo-widget.ui
  (:require [cljsjs.semantic-ui-react]))


;; Easy handle to the top-level extern for semantic-ui-react
(def semantic-ui js/semanticUIReact)

(defn component
  "Get a component from sematic-ui-react:

    (component \"Button\")
    (component \"Menu\" \"Item\")"
  [k & ks]
  (if (seq ks)
    (apply goog.object/getValueByKeys semantic-ui k ks)
    (goog.object/get semantic-ui k)))
