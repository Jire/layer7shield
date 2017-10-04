@file:JvmName("Settings")

package org.jire.layer7shield

/* filter */
var duration = 60000L
var requests_per_duration = 300

/* access log */
var path = "C:/nginx/logs/access.log"
var parse_duration = 500L
var parse_buffer_size = 1024 * 8192

/* logging */
var log_requests = true
var log_bans = true
var log_pid = true

/* firewall */
var add_rule = "netsh advfirewall firewall add rule name=\"Layer7Shield\" dir=in interface=any action=block protocol=TCP localport=80 remoteip=%s/32"
var delete_rule = "netsh advfirewall firewall delete rule name=\"Layer7Shield\" dir=in protocol=TCP localport=80 remoteip=%s/32"