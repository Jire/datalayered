plugins {
	java
	kotlin("jvm") version "1.4.30-M1"
}

group = "org.jire"
version = "0.1.0"

repositories {
	jcenter()
	maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	
	testImplementation("junit", "junit", "4.12")
	
	implementation("mysql", "mysql-connector-java", "8.0.22")
	implementation("com.zaxxer", "HikariCP", "3.4.5")
	implementation("net.openhft", "chronicle-map", "3.20.84")
}