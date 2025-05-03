#lang racket

(define p #px"(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)")

(define m (regexp-match p "turn off 0,0 through 999,999"))
(println (list-ref m 1))
(println (string->number (list-ref m 5)))
; (when m
;   (define action (list-ref m 1))
;   (define x1 (string->number (list-ref m 2)))
;   (define y1 (string->number (list-ref m 3)))
;   (define x2 (string->number (list-ref m 4)))
;   (define y2 (string->number (list-ref m 5)))
;   (println action x1 y1 x2 y2))
; (regexp-match #px"(ca|cb) (\\d+)" "ca 10")

; (map string->number
;        (regexp-match* #px"data-symbol='(\\d+)'"
;                       "data-symbol='1'
;                        data-symbol='2'
;                        data-symbol='3'
;                        data-symbol='4'"
;                       #:match-select second))