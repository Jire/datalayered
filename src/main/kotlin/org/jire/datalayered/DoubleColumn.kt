package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface DoubleColumn : Column {
	
	val default: Double
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readDouble(pointer(key))
	
	operator fun set(key: Long, value: Double) = OS.memory().writeDouble(pointer(key), value)
	
}