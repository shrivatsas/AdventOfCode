(defn valid-password [password]
  (not (re-find #"[iol]" password)))

(println (valid-password "hxbyyiwwxba"))