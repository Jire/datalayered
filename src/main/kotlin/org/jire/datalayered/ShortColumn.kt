package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface ShortColumn : Column {

	val default: Short
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readShort(pointer(key))
	
	operator fun set(key: Long, value: Short) = OS.memory().writeShort(pointer(key), value)

}