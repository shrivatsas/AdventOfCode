#lang racket

(define lights (make-vector 1000 (make-vector 1000 #f)))
(define p #px"(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)")

(define (parse-line line)
  (define m (regexp-match p line))
  (when m
    (define action (list-ref m 1))
    (define x1 (string->number (list-ref m 2)))
    (define y1 (string->number (list-ref m 3)))
    (define x2 (string->number (list-ref m 4)))
    (define y2 (string->number (list-ref m 5)))

    (for ([x (in-range x1 (add1 x2))])
      (for ([y (in-range y1 (add1 y2))])
        (cond
          [(equal? action "turn on") (vector-set! (vector-ref lights x) y #t)]
          [(equal? action "turn off") (vector-set! (vector-ref lights x) y #f)]
          [(equal? action "toggle") (vector-set! (vector-ref lights x) y (not (vector-ref (vector-ref lights x) y)))]
          )))))


(define (process-file filename)
  (with-input-from-file filename
    (λ ()
      (let loop ()
        (let ((line (read-line)))
          (if (eof-object? line)
              (void)
              (begin (parse-line line)
                (loop))))))))

(process-file "../6.input")

; (define (is-true? x) (if x 1 0))
; (count is-true? (flatten lights))

(define (count-lights grid)
  (apply + (map (lambda (row) (count #t row)) grid)))

(println (count-lights lights))