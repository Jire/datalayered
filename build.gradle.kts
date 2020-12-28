plugins {
	java
	kotlin("jvm") version "1.4.30-M1"
	id("me.champeau.gradle.jmh") version "0.5.2" apply true
}

group = "org.jire"
version = "0.1.0"

repositories {
	jcenter()
	maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))
	
	testImplementation("junit", "junit", "4.12")
	
	implementation("mysql", "mysql-connector-java", "8.0.22")
	implementation("com.zaxxer", "HikariCP", "3.4.5")
	implementation("net.openhft", "chronicle-map", "3.20.84")
	implementation("net.openhft", "compiler", "2.4.1")
	implementation("it.unimi.dsi", "fastutil", "8.4.4")
}

jmh {
	duplicateClassesStrategy = DuplicatesStrategy.EXCLUDE
}