package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface FloatColumn : Column {
	
	val default: Float
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readFloat(pointer(key))
	
	operator fun set(key: Long, value: Float) = OS.memory().writeFloat(pointer(key), value)
	
}