package org.jire.datalayered

interface Column {
	
	val size: Int
	val name: String
	val table: Table
	
	val index: Int
	val offset: Long
	
	fun writeDefault(address: Long)
	
	fun init()
	
}