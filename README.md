# Layer7Shield
_A primitive but effective shield against layer 7 DoS attacks for NGINX on Windows_

[![Build Status](https://travis-ci.org/Jire/layer7shield.svg?branch=master)](https://travis-ci.org/Jire/layer7shield)
[![License](https://img.shields.io/github/license/Jire/layer7shield.svg)](https://github.com/Jire/layer7shield/blob/master/LICENSE.txt)

Layer7Shield has a very simple configuration, found in `layer7shield.ini`:

**[filter]**
* **duration** - The duration, in milliseconds, to ban/unban filtered IPs
* **requests_per_duration** - The maximum amount of requests per duration before banning

**[access log]**
* **path** - The absolute path of the NGINX access log file (typically `access.log`)
* **parse_duration** - The duration, in milliseconds, to parse the access log
* **parse_buffer_size** - The size, in bytes, of the parse buffer

**[logging]**
* **log_requests** - Whether or not to log requests
* **log_bans** - Whether or not to log bans
* **log_pid** - Whether or not to report Layer7Shield's process ID on startup

**[firewall]**
* **add_rule** - The format of the add rule command, used to block IPs
* **delete_rule** - The format of the delete rule command, used to unblock IPs