@file:JvmName("Layer7Shield")

package org.jire.layer7shield

import org.apache.commons.io.input.Tailer
import org.ini4j.IniPreferences
import java.io.File
import org.ini4j.Ini
import java.lang.management.ManagementFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

const val INI_FILE = "layer7shield.ini"

fun main(args: Array<String>) {
	// load preferences in safe way so can make minimal configs
	IniPreferences(Ini(File(INI_FILE))).run {
		node("filter")?.run {
			duration = get("duration", duration.toString()).toLong()
			requests_per_duration = get("requests_per_duration", requests_per_duration.toString()).toInt()
		}
		node("access log")?.run {
			path = get("path", path)
			parse_duration = get("parse_duration", parse_duration.toString()).toLong()
			parse_buffer_size = get("parse_buffer_size", parse_buffer_size.toString()).toInt()
		}
		node("logging")?.run {
			log_requests = get("log_requests", log_requests.toString()).toBoolean()
			log_bans = get("log_bans", log_bans.toString()).toBoolean()
			log_pid = get("log_pid", log_pid.toString()).toBoolean()
		}
		node("firewall")?.run {
			rule_name = get("rule_name", rule_name)
			
			add_rule_format = get("add_rule_format", add_rule_format)
			delete_rule_format = get("delete_rule_format", delete_rule_format)
		}
	}
	
	val logFile = File(path)
	if (!logFile.exists())
		println("The specified access log file \"$path\" does not exist.")
	else {
		if (log_pid) println("Layer7Shield [${ManagementFactory.getRuntimeMXBean().name}]")
		with(Executors.newScheduledThreadPool(2)) { // 2 threads, one for tailer one for clear
			execute(Tailer.create(logFile, LogTailer, parse_duration, true, false, parse_buffer_size))
			scheduleAtFixedRate({
				Firewall.clear() // first clear firewall
				RequestFilter.clear() // then all requests
			}, duration, duration, TimeUnit.MILLISECONDS)
		}
	}
}