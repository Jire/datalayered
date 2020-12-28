package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class DoubleColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Double = 0.0
) : AbstractColumn(8, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readDouble(pointer(key))
	
	operator fun set(key: Long, value: Double) = OS.memory().writeDouble(pointer(key), value)
	
}