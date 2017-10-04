package org.jire.layer7shield

object Firewall {
	
	private val blockedIPs = HashSet<String>()
	
	fun ban(ip: String) {
		if (!blockedIPs.contains(ip)) {
			blockedIPs.add(ip)
			Runtime.getRuntime().exec(add_rule.format(ip))
			
			if (log_bans) println("Banned $ip")
		}
	}
	
	fun unban(ip: String) {
		if (blockedIPs.contains(ip)) {
			Runtime.getRuntime().exec(delete_rule.format(ip))
			blockedIPs.remove(ip)
			
			if (log_bans) println("Unbanned $ip")
		}
	}
	
	fun clear() {
		blockedIPs.forEach { unban(it) }
		blockedIPs.clear()
	}
	
}