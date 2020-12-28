package org.jire.datalayered

interface Database : Datalayered {
	
	override fun implementedType() = AbstractDatabase::class

	val name: String
	
	fun init()

}