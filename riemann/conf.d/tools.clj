; straight up log the event
(defn log-info [event]
  (info event)
)

; generate an email from an event
(def email
  (mailer {:from "opsbot@example.com"})
)

(def slack-credentials {
  :account "", :token ""})

(def chat-ops
  (slack slack-credentials {
    :username "opsbot"
    :channel "#chatops"
    :icon ":smile:"}
  )
)

(def alert-ops
   (email "opsalert@example.com")
   (fn [event] (info "alert ops" event))
)

(def alert-ops-throttled
  ; throttle to 1000 events every hour
  (throttle 1000 3600
    ; of the 1000 throttled above, allow 5 every hour
    (rollup 5 3600
      alert-ops
    )
  )
)

(def tell-ops
   (chat-ops event)
)
