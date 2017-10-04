package org.jire.layer7shield

object RequestFilter {
	
	private val ipToRequests = HashMap<String, Int>()
	
	fun filter(ip: String, request: String) {
		if (print_logs) println("$ip $request")
		
		val requests = ipToRequests.getOrDefault(ip, 0)
		if (requests >= 100) Firewall.block(ip)
		else ipToRequests.put(ip, requests + 1)
	}
	
	fun clear() {
	
	}
	
}