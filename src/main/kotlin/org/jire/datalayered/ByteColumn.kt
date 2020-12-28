package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface ByteColumn : Column {
	
	val default: Byte
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readByte(pointer(key))
	
	operator fun set(key: Long, value: Byte) = OS.memory().writeByte(pointer(key), value)
	
}