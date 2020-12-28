package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class PointerColumn(
	size: Int,
	name: String, table: Table,
	val default: Long
) : AbstractColumn(size, name, table) {
	
	val allocSize = size.toLong()
	
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