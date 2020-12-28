package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class CharColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Char = 0.toChar()
) : AbstractColumn(2, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readShort(pointer(key)).toChar()
	
	operator fun set(key: Long, value: Char) = OS.memory().writeShort(pointer(key), value.toShort())
	
}