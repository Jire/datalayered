package org.jire.datalayered

import net.openhft.chronicle.core.OS

abstract class ByteColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	val default: Byte = 0
) : AbstractColumn(1, name, table) {
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readByte(pointer(key))
	
	operator fun set(key: Long, value: Byte) = OS.memory().writeByte(pointer(key), value)
	
}