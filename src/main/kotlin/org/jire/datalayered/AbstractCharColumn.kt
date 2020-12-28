package org.jire.datalayered

abstract class AbstractCharColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Char = 0.toChar()
) : AbstractColumn(2, name, table), CharColumn