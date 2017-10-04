package org.jire.layer7shield

import org.apache.commons.io.input.TailerListenerAdapter

object LogTailer : TailerListenerAdapter() {
	override fun handle(line: String) {
		val split = line.split(" ")
		
		val ip = split[0]
		val request = split[6]
		
		RequestFilter.filter(ip, request)
	}
}