package org.jire.layer7shield

/* filter */
var duration = 60000L
var requests_per_duration = 100

/* access log */
var path = "C:/nginx/logs/access.log"
var parse_duration = 500L
var parse_buffer_size = 1024 * 8192

/* logging */
var log_requests = true
var log_bans = true
var log_pid = true

/* firewall */
var rule_name = "Layer7Shield"
var add_rule_format = "netsh advfirewall firewall add rule name=\"%s\" dir=in interface=any action=block remoteip=%s/32"
var delete_rule_format = "netsh advfirewall firewall delete rule name=\"%s\" dir=in remoteip=%s/32"