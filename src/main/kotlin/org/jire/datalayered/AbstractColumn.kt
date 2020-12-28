package org.jire.datalayered

abstract class AbstractColumn(
	final override val size: Int,
	override val name: String,
	final override val table: Table
) : Column {
	
	override val index = table.nextIndex
	override val offset = table.size
	
	fun pointer(key: Long) = key + offset
	
	override fun init() {
		table.size += size
		table.nextIndex++
		table.columns.add(this)
	}
	
}