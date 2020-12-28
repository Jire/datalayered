package org.jire.datalayered

interface Database : Datalayered {
	
	val name: String
	
	fun init()

}