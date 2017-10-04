@file:JvmName("Layer7Shield")

package org.jire.layer7shield

import org.ini4j.IniPreferences
import java.io.File
import org.ini4j.Ini

const val INI_FILE = "layer7shield.ini"

fun main(args: Array<String>) {
	val ini = IniPreferences(Ini(File(INI_FILE)))
	
	val accessLogFileLocation = ini.node("files").get("access_log_file", null)
	val accessLogFile = File(accessLogFileLocation)
	if (!accessLogFile.exists()) {
		println("The specified access log file \"$accessLogFileLocation\" does not exist.")
		return
	}
	
	val firewallNode = ini.node("firewall")
	val ruleName = firewallNode.get("rule_name", null)
	val addRuleFormat = firewallNode.get("add_rule_format", null)
	val deleteRuleFormat = firewallNode.get("delete_rule_format", null)
	
	watch(accessLogFile, ruleName, addRuleFormat, deleteRuleFormat)
}