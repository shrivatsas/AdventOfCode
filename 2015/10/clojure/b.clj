(defn look-and-say [n]
  (let [str-n (str n)]
    (loop [result ""
           i 0]
      (if (>= i (count str-n))
        result
        (let [count (re-find #"(?=(\d)\1*)" str-n)
              digit (str-n i)
              next-i (+ i (count count))]
          (recur (str result (count count) digit) next-i))))))

(count (reduce (fn [n _] (look-and-say n)) "1113222113" (range 40)))