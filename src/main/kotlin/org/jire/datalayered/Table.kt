package org.jire.datalayered

interface Table : Datalayered {
	
	val database: Database
	
	val name: String
	val maxEntries: Long
	
	var nextIndex: Int
	var size: Long
	
	var mapAddress: Long
	var mapSize: Long
	
	var defaultKey: Long
	val columns: MutableList<Column>
	
	fun init()
	
	fun newKey(defaultKey: Boolean): Long
	fun newKey() = newKey(false)
	
	operator fun invoke() = newKey()
	
	fun freeKey(key: Long)
	
	fun free()
	
}