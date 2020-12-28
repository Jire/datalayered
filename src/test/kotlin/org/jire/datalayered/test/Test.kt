package org.jire.datalayered.test

import org.jire.datalayered.generated.datalayered

object Test {
	
	@JvmStatic
	fun main(args: Array<String>) {
		val datalayered = datalayered()
		datalayered.init()
		
		val members = datalayered.members
		println("default UID ${members.uid[members.defaultKey]}")
		
		val member1 = datalayered.members.newKey()
		println("member1=$member1")
		val member1UID = members.uid[member1]
		println("member1UID=$member1UID")
	}

}