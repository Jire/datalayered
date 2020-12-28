package org.jire.datalayered

abstract class AbstractLongColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Long = 0
) : AbstractColumn(8, name, table), LongColumn