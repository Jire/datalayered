package org.jire.datalayered

abstract class AbstractDoubleColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Double = 0.0
) : AbstractColumn(8, name, table), DoubleColumn