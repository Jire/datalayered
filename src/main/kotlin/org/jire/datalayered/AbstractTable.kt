package org.jire.datalayered

import net.openhft.chronicle.core.OS
import java.io.RandomAccessFile
import java.nio.channels.FileChannel

abstract class AbstractTable(
	override val database: Database,
	override val name: String,
	override val maxEntries: Long
) : Table {
	
	override var nextIndex = 0
	override var size = 0L
	
	override var mapAddress = 0L
	override var mapSize = 0L
	
	@Volatile
	var offset = 0L
	
	override var defaultKey = 0L
	override val columns: MutableList<Column> = ArrayList()
	
	abstract fun initColumns()
	
	override fun init() {
		initColumns()
		
		mapSize = size * maxEntries
		val file = RandomAccessFile("${name}.datalayered", "rw")
		mapAddress = OS.map(file.channel, FileChannel.MapMode.READ_WRITE, 0, mapSize)//OS.memory().allocate(mapSize)
		
		defaultKey = newKey(true)
	}
	
	override fun newKey(defaultKey: Boolean): Long {
		val address = mapAddress + offset
		if (defaultKey) {
			for (column in columns) {
				column.writeDefault(address)
			}
		} else {
			OS.memory().copyMemory(this.defaultKey, address, size)
		}
		offset += size
		return address
	}
	
	override fun freeKey(key: Long) {
		//OS.memory().freeMemory(key, size)
		throw UnsupportedOperationException("$key")
	}
	
	override fun free() {
		//OS.memory().freeMemory(mapAddress, mapSize)
		OS.unmap(mapAddress, mapSize)
	}
	
}