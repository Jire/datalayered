package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class IntColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Int = 0
) : AbstractColumn(4, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readInt(pointer(key))
	
	operator fun set(key: Long, value: Int) = OS.memory().writeInt(pointer(key), value)
	
}