package org.jire.datalayered.test

import org.jire.datalayered.Datalayered

object Test {
	
	@JvmStatic
	fun main(args: Array<String>) {
		val main = Datalayered(main::class)
		val members = main.members
		
		val key = members()
		val uid = members.uid
		
		uid[key] = 1337
		println(uid[key])
	}
	
}