(ns kopiplayer.debug)

(defmacro dbg [form]
  `(let [result# ~form]
     (prn '~form "=>" result#)
     result#))

(def ^:dynamic *recursion-depth* 0)
(def ^:dynamic *max-recursion-depth* 4)

(defn print-indented [msg x]
  (print (str (apply str (repeat *recursion-depth* "| "))))
  (print msg " ")
  (prn x))

(defmacro defndbg [name arguments & body]
  `(defn ~name ~arguments
     (binding [*recursion-depth* (inc *recursion-depth*)]
       (if (> *recursion-depth* *max-recursion-depth*)
         (print-indented "max recursion depth hit for " '~name)
         ;; else
         (do
           (print-indented "calling " '~name)
           (doall (map #(when-not (:dbghide (meta %1)) 
                          (print-indented (str " - " %1 ": ") %2))
                       '~arguments
                       ~arguments))
           (let [result# (do ~@body)]
             (print-indented (str "result of " '~name ": ") result#)
             result#))))))