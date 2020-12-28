package org.jire.datalayered

abstract class AbstractFloatColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Float = 0F
) : AbstractColumn(4, name, table), FloatColumn