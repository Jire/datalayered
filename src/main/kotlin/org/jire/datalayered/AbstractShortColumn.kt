package org.jire.datalayered

abstract class AbstractShortColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Short = 0
) : AbstractColumn(2, name, table), ShortColumn