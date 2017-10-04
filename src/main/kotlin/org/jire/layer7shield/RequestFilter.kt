package org.jire.layer7shield

object RequestFilter {
	
	private val ipToRequests = HashMap<String, Int>()
	
	fun filter(ip: String, request: String) {
		if (!request.contains('.') || request.contains(".php")) { // only for dynamic workloads
			if (log_requests) println("$ip $request")
			
			val requests = ipToRequests.getOrDefault(ip, 0)
			if (requests == requests_per_duration) Firewall.ban(ip)
			else if (requests < requests_per_duration) ipToRequests.put(ip, requests + 1)
		}
	}
	
	fun clear() {
		ipToRequests.clear()
	}
	
}