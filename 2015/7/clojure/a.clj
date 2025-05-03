(ns a
  (:require [clojure.string :as string])
  (:require [clojure.java.io :as io]))

(defn read-lines [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))

;; (defn get-lines [filename]
;;   (string/split-lines (slurp filename)))

(def instructions (read-lines "../7.input"))

;; (defn is-digit? [s]
;;   (try
;;     (Integer/parseInt s)
;;     true
;;     (catch Exception e)
;;       false))

(defn is-digit? [s]
  (if (re-find #"^\d+$" s)
    true
    false))

;; ---
(def wires (atom {}))

(doseq [inst instructions]
  (let [[lhs rhs] (clojure.string/split inst #" -> ")]
    (swap! wires assoc rhs (if (is-digit? lhs)
                             (Integer/parseInt lhs)
                             lhs))))

;; (println @wires)
;; ---
;; (def wires (into (transient {})
;;                 (map (fn [line]
;;                        (let [[k v] (clojure.string/split line #" -> ")]
;;                          [v (if (is-digit? k)
;;                              (Integer/parseInt k)
;;                              k)]))
;;                      instructions)))

;; (println (get wires "a"))
;; ---
                                                                        
;; (defn populateWires
;;   )
;; (defn evaluate [instructions wire]
;;   (let [signals (atom {})]
;;     (doseq [instruction instructions]
;;       (let [[_op a op b _ c] (re-find #"^([a-z0-9]+) (AND|OR|LSHIFT|RSHIFT|NOT) ([a-z0-9]+) -> ([a-z]+)$" "1 AND gd -> ge")]
;;         (println _op a op b _ c)
;;         (case op
;;         "AND" (swap! signals assoc c (bit-and (get @signals a) (get @signals b)))
;;         "OR" (swap! signals assoc c (bit-or (get @signals a) (get @signals b)))
;;         "LSHIFT" (swap! signals assoc c (bit-shift-left (get @signals a) (get @signals b)))
;;         "RSHIFT" (swap! signals assoc c (bit-shift-right (get @signals a) (get @signals b)))
;;         "NOT" (swap! signals assoc c (bit-not (get @signals a)))
;;         (swap! signals assoc c (Integer/parseInt a)))))
;;     (get @signals wire)))

(defn solve [x]
  (println "pre - " x " - " (get @wires x))
  (cond
    (number? x) x
    (number? (get @wires x)) (get @wires x)
    (is-digit? x) (Integer/parseInt x)
    (re-find #"^NOT ([a-z0-9]+)$" x) (swap! wires assoc x (bit-not (solve (second (clojure.string/split x #" ")))))
    ;; (is-digit? (get wires x)) (Integer/parseInt (get wires x))
    :else
    (let [[_op a op b] (re-find #"^([a-z0-9]+) (AND|OR|LSHIFT|RSHIFT) ([a-z0-9]+)$" (get @wires x))]
      (println "post - " _op " - " a op b " - " (get @wires x))
      (cond op
        (= op "AND") (do
                       (swap! wires assoc x (bit-and (solve a) (solve b)))
                       (get @wires x))
        (= op "OR") (do 
                      (swap! wires assoc x (println "OR" (solve a) (solve b)))
                      (get @wires x))
        (= op "LSHIFT") (do
                          (swap! wires assoc x (bit-shift-left (solve a) (solve b)))
                          (get @wires x))
        (= op "RSHIFT") (do
                          (swap! wires assoc x (bit-shift-right (solve a) (solve b)))
                          (get @wires x))
        :else (solve (get @wires x)))
      )))
  ;; (when (number? x) x)
  ;; (when (number? (get wires x)) (get wires x))
  ;; (when (is-digit? x) (Integer/parseInt x))
  ;; (when (is-digit? (get wires x)) (Integer/parseInt (get wires x)))

(println (solve "g"))
;; (swap! wires assoc "t" (bit-shift-left (solve "c") (solve "1")))
;; (print (get @wires "t"))
;; (println (solve "c") (solve "1"))
;; (println (second (clojure.string/split "NOT h" #" ")))