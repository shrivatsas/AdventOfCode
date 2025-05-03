(defn increment-password [password]
  (let [chars (map int (reverse (str password)))
        carry 1]
    (map (fn [c] (let [c (+ c carry)
                      carry (if (>= c 122) 1 0)]
                (char (+ c (if (= c 123) -25 0)))))
         chars)))

(defn valid-password? [password]
  (let [has-straight (some #(re-find #"abc|bcd|cde|def|efg|fgh|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz" %) (map str (partition 3 1 password))),
        has-no-forbidden-chars (not (re-find #"[iol]" password))
        has-two-pairs (> (count (re-seq #"(.)\1" password)) 1)]
    (and has-straight has-no-forbidden-chars has-two-pairs)))

(defn next-password [password]
  (loop [password (increment-password password)]
    (if (valid-password? password)
      (apply str (reverse password))
      (recur (increment-password password)))))

(next-password "hxbxwxba") 
