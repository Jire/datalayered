package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface CharColumn : Column {
	
	val default: Char
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readShort(pointer(key)).toChar()
	
	operator fun set(key: Long, value: Char) = OS.memory().writeShort(pointer(key), value.toShort())
	
}