package org.jire.datalayered

import net.openhft.chronicle.core.OS

interface IntColumn : Column {
	
	override fun implementedType() = AbstractIntColumn::class
	
	val default: Int
	
	override fun writeDefault(address: Long) = set(address, default)
	
	operator fun get(key: Long) = OS.memory().readInt(pointer(key))
	
	operator fun set(key: Long, value: Int) = OS.memory().writeInt(pointer(key), value)
	
}