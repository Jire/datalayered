package org.jire.datalayered

abstract class AbstractPointerColumn(
	size: Int,
	name: String, table: Table,
	override val default: Long
) : AbstractColumn(size, name, table), PointerColumn