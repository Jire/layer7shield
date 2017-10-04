@file:JvmName("EnableDebugPrivilege")

package org.jire.layer7shield

import com.sun.jna.Native
import com.sun.jna.platform.win32.*

fun enableDebugPrivilege() {
	val hToken = WinNT.HANDLEByReference()
	try {
		if (!Advapi32.INSTANCE.OpenProcessToken(Kernel32.INSTANCE.GetCurrentProcess(),
				WinNT.TOKEN_QUERY or WinNT.TOKEN_ADJUST_PRIVILEGES, hToken)) {
			val lastError = Native.getLastError()
			System.err.println("OpenProcessToken failed. Error: " + lastError)
			throw Win32Exception(lastError)
		}
		
		val luid = WinNT.LUID()
		if (!Advapi32.INSTANCE.LookupPrivilegeValue(null, WinNT.SE_DEBUG_NAME, luid)) {
			val lastError = Native.getLastError()
			System.err.println("LookupprivilegeValue failed. Error: " + lastError)
			throw Win32Exception(lastError)
		}
		
		val tkp = WinNT.TOKEN_PRIVILEGES(1)
		tkp.Privileges[0] = WinNT.LUID_AND_ATTRIBUTES(luid, WinDef.DWORD(WinNT.SE_PRIVILEGE_ENABLED.toLong()))
		
		if (!Advapi32.INSTANCE.AdjustTokenPrivileges(hToken.value, false,
				tkp, 0, null, null)) {
			val lastError = Native.getLastError()
			System.err.println("AdjustTokenPrivileges failed. Error: " + lastError)
			throw Win32Exception(lastError)
		}
	} finally {
		Kernel32.INSTANCE.CloseHandle(hToken.value)
	}
}