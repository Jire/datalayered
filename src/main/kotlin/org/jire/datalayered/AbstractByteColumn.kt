package org.jire.datalayered

abstract class AbstractByteColumn
@JvmOverloads
constructor(
	name: String, table: Table,
	override val default: Byte = 0
) : AbstractColumn(1, name, table), ByteColumn