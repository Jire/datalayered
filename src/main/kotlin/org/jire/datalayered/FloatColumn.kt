package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class FloatColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Float = 0F
) : AbstractColumn(4, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readFloat(pointer(key))
	
	operator fun set(key: Long, value: Float) = OS.memory().writeFloat(pointer(key), value)
	
}