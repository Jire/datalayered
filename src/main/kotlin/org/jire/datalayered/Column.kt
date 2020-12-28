package org.jire.datalayered

interface Column : Datalayered {
	
	val size: Int
	val name: String
	val table: Table
	
	val index: Int
	val offset: Long
	
	fun pointer(key: Long) = key + offset
	
	fun writeDefault(address: Long)
	
	fun init()
	
}