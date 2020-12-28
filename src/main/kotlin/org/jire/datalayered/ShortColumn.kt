package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class ShortColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Short = 0
) : AbstractColumn(2, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readShort(pointer(key))
	
	operator fun set(key: Long, value: Short) = OS.memory().writeShort(pointer(key), value)
	
}