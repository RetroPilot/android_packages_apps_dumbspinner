This is just a dumb spinner used during RetrOS install process.


Fire an intent to change the spinner status, eg:
`am start -n org.retropilot.retros.dumbspinner/org.retropilot.retros.dumbspinner.MainActivity --es "loading_reason" "WHY ARE WE WAITING????"`

Fork select UI
`am start -n org.retropilot.retros.dumbspinner/org.retropilot.retros.dumbspinner.fork_select`

launches android settings, will add some extra ui at some point - saves updatign shell scripts
`am start -n org.retropilot.retros.dumbspinner/org.retropilot.retros.dumbspinner.wifi`


scope creeping a bit, not really a serious app - stop gap

The app works by using intents, it doesn't manage it's own state. We don't plan to use this long term so the... questionable architecture isn't really relevant - it works. We'll end up moving over to a qt based installer, that way we can target any platform & simulator users.
