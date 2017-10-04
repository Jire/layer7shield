package org.jire.layer7shield

object Firewall {
	
	private val blockedIPs = HashSet<String>()
	
	fun block(ip: String) {
		if (!blockedIPs.contains(ip)) {
			blockedIPs.add(ip)
			
			// TODO block in firewall
		}
	}
	
	fun unblock(ip: String) {
		if (blockedIPs.contains(ip)) {
			// TODO unblock in firewall
		}
	}
	
	fun clear() {
		blockedIPs.forEach { unblock(it) }
		blockedIPs.clear()
	}
	
}