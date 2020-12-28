package org.jire.datalayered

abstract class AbstractBooleanColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Boolean = false
) : AbstractColumn(1, name, table), BooleanColumn