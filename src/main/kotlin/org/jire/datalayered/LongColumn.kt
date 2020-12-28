package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class LongColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Long = 0
) : AbstractColumn(8, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readLong(pointer(key))
	
	operator fun set(key: Long, value: Long) = OS.memory().writeLong(pointer(key), value)
	
}