package org.jire.layer7shield

/* layer7shield */
var duration = 60000L
var requests_per_duration = 100

var print_logs = true
var print_blocks = true

/* log file */
var path = "C:/nginx/logs/access.log"
var parse_duration = 500L
var parse_buffer_size = 1024 * 8192

/* firewall */
var rule_name = "Layer7Shield"

var add_rule_format = "netsh advfirewall firewall add rule name=\"%s\" dir=in interface=any action=block remoteip=%s/32"
var delete_rule_format = "netsh advfirewall firewall delete rule name=\"%s\" dir=in remoteip=%s/32"