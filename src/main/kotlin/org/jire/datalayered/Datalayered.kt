package org.jire.datalayered

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.openhft.compiler.CompilerUtils
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

interface Datalayered {
	
	companion object {
		const val FILE_EXTENSION = ".datalayered"
		
		val classToAbstract = mapOf(
			ByteColumn::class to AbstractByteColumn::class,
			ShortColumn::class to AbstractShortColumn::class,
			IntColumn::class to AbstractIntColumn::class,
			LongColumn::class to AbstractLongColumn::class,
			FloatColumn::class to AbstractFloatColumn::class,
			DoubleColumn::class to AbstractDoubleColumn::class,
			CharColumn::class to AbstractCharColumn::class,
			BooleanColumn::class to AbstractBooleanColumn::class,
			PointerColumn::class to AbstractPointerColumn::class
		)
		
		val classToGenerated: Object2ObjectMap<KClass<*>, Class<*>> = Object2ObjectOpenHashMap()
		
		fun nativeColumnClass(tableName: String, type: KClass<*>): Pair<String, String> {
			val simpleName = type.simpleName!!
			val abstract = classToAbstract[type.superclasses[0]] ?: throw UnsupportedOperationException("$type")
			val name = simpleName
			
			val className = "$simpleName$\$DATALAYERED_COLUMN"
			var string =
				"\n\t\tpublic static final class $className extends ${abstract.qualifiedName} implements ${type.qualifiedName} {\n\t\t\t"
			string += "public $className(org.jire.datalayered.Table table) {\n\t\t\t\t"
			string += "super(\"$name\", table);\n\t\t\t}\n\t\t}\n"
			return className to string
		}
		
		fun nativeTableClass(databaseName: String, type: KClass<*>): Pair<String, String> {
			val simpleName = type.simpleName!!
			val className = "$simpleName$\$DATALAYERED_TABLE"
			val name = simpleName
			
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
				val columnCode = nativeColumnClass(name, memberType)
				string += columnCode.second
				string += "\n\t\tpublic final $memberTypeName $memberName = new ${columnCode.first}(this);\n\n"
				string += "\n\t\t@Override public $memberTypeName get${memberName.capitalize()}() { return $memberName; }\n\n"
				initColumns += "\n\t\t\t$memberName.init();"
			}
			string += "\t\tpublic $className(org.jire.datalayered.Database database) {\n\t\t\t" +
					"super(database, \"$name\", 10000);\n\t\t}"
			string += "\n\n\t\t$initColumns\n\t\t}\n\n\t}"
			return className to string
		}
		
		fun <T : Database> nativeDatabaseClass(type: KClass<T>): Pair<String, String> {
			val annotation = type.annotations.firstOrNull { it.annotationClass == DatalayeredName::class }
			val simpleName = type.simpleName!!
			val name = simpleName.toLowerCase()
			val className = "$simpleName$\$DATALAYERED_DATABASE"
			var string = """package org.jire.datalayered.generated;
			
public final class $className extends org.jire.datalayered.AbstractDatabase implements ${type.qualifiedName} {
			
	public $className() {
		super("$name");
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
				val (tableClassName, tableCode) = nativeTableClass(name, memberType)
				string += tableCode
				declareMembers += "\tpublic final ${memberType.qualifiedName} $memberName = new $tableClassName(this);\n"
				declareMembers += "\t@Override public ${memberType.qualifiedName} get${memberName.capitalize()}() { return $memberName; }\n"
				initMembers += "\n\t\t$memberName.init();"
			}
			string += "\n\n$declareMembers\n\t$initMembers\n\t}\n\n}"
			return className to string
		}
		
		@Suppress("UNCHECKED_CAST")
		fun <T : Database> nativeClass(type: KClass<T>): Class<T> {
			val cached = classToGenerated[type]
			if (cached != null) return cached as Class<T>
			
			val classCode = nativeDatabaseClass(type)
			val newClass = CompilerUtils.CACHED_COMPILER.loadFromJava(
				"org.jire.datalayered.generated.${classCode.first}", classCode.second
			) as Class<T>
			classToGenerated[type] = newClass
			return newClass
		}
		
		operator fun <T : Database> invoke(type: KClass<T>): T {
			val nativeKClass = nativeClass(type)
			return nativeKClass.newInstance().apply { init() }
		}
	}
	
}