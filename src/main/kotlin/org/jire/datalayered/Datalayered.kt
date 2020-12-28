package org.jire.datalayered

import net.openhft.compiler.CompilerUtils
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

interface Datalayered {
	
	fun implementedType(): KClass<*>

	companion object {
		const val FILE_EXTENSION = ".datalayered"
		
		val classToAbstract = mapOf(
			IntColumn::class to AbstractIntColumn::class,
			LongColumn::class to AbstractLongColumn::class
		)
		
		fun nativeColumnClass(type: KClass<*>): Pair<String, String> {
			val simpleName = type.simpleName
			val abstract = classToAbstract[type.superclasses[0]] ?: throw UnsupportedOperationException("$type")
			
			val className = "$simpleName$\$DATALAYERED_COLUMN"
			var string =
				"\n\t\tpublic static final class $className extends ${abstract.qualifiedName} implements ${type.qualifiedName} {\n\t\t\t"
			string += "public $className(org.jire.datalayered.Table table) {\n\t\t\t\t"
			string += "super(\"points\", table);\n\t\t\t}\n\t\t}\n"
			return className to string
		}
		
		fun nativeTableClass(type: KClass<*>): Pair<String, String> {
			val simpleName = type.simpleName
			val className = "$simpleName$\$DATALAYERED_TABLE"
			
			var initColumns = "public void initColumns() {\t\t"
			
			var string =
				"\n\tpublic static final class $className extends org.jire.datalayered.AbstractTable implements ${type.qualifiedName} {\n\t"
			for (member in type.declaredMembers) {
				member.isAccessible = true
				
				val memberType = member.returnType.jvmErasure
				if (!memberType.isSubclassOf(Column::class)) {
					throw UnsupportedOperationException("")
				}
				val memberName = member.name
				val memberTypeName = memberType.qualifiedName
				val columnCode = nativeColumnClass(memberType)
				string += columnCode.second
				string += "\n\t\tpublic final $memberTypeName $memberName = new ${columnCode.first}(this);\n\n"
				string += "\n\t\t@Override public $memberTypeName get${memberName.capitalize()}() { return $memberName; }\n\n"
				initColumns += "\n\t\t\t$memberName.init();"
			}
			string += "\t\tpublic $className(org.jire.datalayered.Database database) {\n\t\t\t" +
					"super(database, \"members\", 10000);\n\t\t}"
			string += "\n\n\t\t$initColumns\n\t\t}\n\n\t}"
			return className to string
		}
		
		fun <T : Database> nativeDatabaseClass(type: KClass<T>): Pair<String, String> {
			val simpleName = type.simpleName
			val className = "$simpleName$\$DATALAYERED_DATABASE"
			var string = """package org.jire.datalayered.generated;
			
public final class $className extends org.jire.datalayered.AbstractDatabase implements ${type.qualifiedName} {
			
	public $className() {
		super("datalayered");
	}
"""
			var declareMembers = ""
			var initMembers = "public void init() {"
			val members = type.declaredMembers
			for (member in members) {
				member.isAccessible = true
				
				val memberName = member.name
				val memberType = member.returnType.jvmErasure
				if (!memberType.isSubclassOf(Table::class)) {
					throw UnsupportedOperationException("")
				}
				val memberTypeName = memberType.simpleName
				val (tableClassName, tableCode) = nativeTableClass(memberType)
				string += tableCode
				declareMembers += "\tpublic final ${memberType.qualifiedName} $memberName = new $tableClassName(this);\n"
				declareMembers += "\t@Override public ${memberType.qualifiedName} get${memberName.capitalize()}() { return $memberName; }\n"
				initMembers += "\n\t\t$memberName.init();"
			}
			string += "\n\n$declareMembers\n\t$initMembers\n\t}\n\n}"
			return className to string
		}
		
		fun <T : Database> nativeClass(type: KClass<T>): Class<T> {
			val classCode = nativeDatabaseClass(type)
			@Suppress("UNCHECKED_CAST")
			return CompilerUtils.CACHED_COMPILER.loadFromJava(
				"org.jire.datalayered.generated.${classCode.first}", classCode.second
			) as Class<T>
		}
		
		operator fun <T : Database> invoke(type: KClass<T>): T {
			val nativeKClass = nativeClass(type)
			return nativeKClass.newInstance().apply { init() }
		}
	}
	
}