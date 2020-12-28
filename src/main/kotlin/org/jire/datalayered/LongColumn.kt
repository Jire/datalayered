package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface LongColumn : Column {
	
	val default: Long
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readLong(pointer(key))
	
	operator fun set(key: Long, value: Long) = OS.memory().writeLong(pointer(key), value)
	
}