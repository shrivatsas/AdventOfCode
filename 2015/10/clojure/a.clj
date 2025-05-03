(defn look-and-say [n]
  (let [str-n (str n)]
    (loop [result ""
           i 0]
      (if (>= i (count str-n))
        result
        (let [count (count (re-seq (re-pattern (str ((str n) i))) (str n)))
              digit ((str n) i)
              next-i (+ i count)]
          (recur (str result count digit) next-i))))))

(count (reduce (fn [n _] (look-and-say n)) "1113222113" (range 40)))
