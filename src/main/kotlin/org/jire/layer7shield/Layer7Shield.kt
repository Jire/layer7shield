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
	println("Layer7Shield [${ManagementFactory.getRuntimeMXBean().name}]")
	
	val ini = IniPreferences(Ini(File(INI_FILE)))
	
	ini.node("layer7shield")?.run {
		duration = get("duration", duration.toString()).toLong()
		requests_per_duration = get("requests_per_duration", requests_per_duration.toString()).toInt()
		
		print_logs = get("print_logs", print_logs.toString()).toBoolean()
		print_blocks = get("print_blocks", print_blocks.toString()).toBoolean()
	}
	ini.node("log file")?.run {
		path = get("path", path)
		parse_duration = get("parse_duration", parse_duration.toString()).toLong()
		parse_buffer_size = get("parse_buffer_size", parse_buffer_size.toString()).toInt()
	}
	ini.node("firewall")?.run {
		rule_name = get("rule_name", rule_name)
		
		add_rule_format = get("add_rule_format", add_rule_format)
		delete_rule_format = get("delete_rule_format", delete_rule_format)
	}
	
	val logFile = File(path)
	if (!logFile.exists())
		println("The specified access log file \"$path\" does not exist.")
	else with(Executors.newScheduledThreadPool(2)) {
		execute(Tailer.create(logFile, LogTailer, parse_duration, true, false, parse_buffer_size))
		scheduleAtFixedRate(RequestFilter::clear, duration, duration, TimeUnit.MILLISECONDS)
	}
}