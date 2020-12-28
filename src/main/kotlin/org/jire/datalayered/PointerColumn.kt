package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface PointerColumn : Column {
	
	val default: Long
	
	val allocSize get() = size.toLong()
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long): Long {
		val address = OS.memory().allocate(allocSize)
		OS.memory().copyMemory(pointer(key), address, allocSize)
		return address
	}
	
	operator fun set(key: Long, value: Long) {
		OS.memory().copyMemory(value, pointer(key), allocSize)
	}
	
}