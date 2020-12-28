package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class BooleanColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Boolean = false
) : AbstractColumn(1, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readByte(pointer(key)).toInt() != 0
	
	operator fun set(key: Long, value: Boolean) = OS.memory().writeByte(pointer(key), if (value) 1 else 0)
	
}