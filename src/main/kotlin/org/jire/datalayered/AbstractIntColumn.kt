package org.jire.datalayered

abstract class AbstractIntColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Int = 0
) : AbstractColumn(4, name, table), IntColumn