@file:JvmName("LogWatcher")

package org.jire.layer7shield

import java.io.File

const val REQUESTS_PER_DURATION = 35

private val ipToRequestTimes = HashMap<String, HashMap<String, Int>>()

internal fun watch(accessLogFile: File, ruleName: String, addRuleFormat: String, deleteRuleFormat: String) {
	var lastReadLine = -1
	
	while (!Thread.interrupted()) {
		val lines = accessLogFile.readLines()
		if (lastReadLine < lines.lastIndex) { // so we don't reparse the same line
			for (lineIndex in lastReadLine + 1..lines.lastIndex) {
				val line = lines[lineIndex]
				
				val split = line.split(" ")
				val ip = split[0]
				val request = split[6]
				
				val requestToTimes = ipToRequestTimes.getOrDefault(ip, HashMap())
				val requests = requestToTimes.getOrDefault(request, 0)
				if (requests >= REQUESTS_PER_DURATION) {
					// block in firewall
					Runtime.getRuntime().exec(addRuleFormat.format(ruleName, ip))
				} else {
					requestToTimes.put(request, requests + 1)
					ipToRequestTimes.put(ip, requestToTimes)
				}
				
				lastReadLine = lineIndex
			}
		}
		
		try {
			Thread.sleep(1000L)
		} catch (ie: InterruptedException) {
			ie.printStackTrace()
			return
		}
	}
}