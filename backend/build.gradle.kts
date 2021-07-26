val ktorVersion = "1.6.1"
val jacksonVersion = "2.12.4"

plugins {
     application
 }

 kotlin {
     jvm {
         withJava()
     }
     sourceSets {
         val jvmMain by getting {}
     }
 }

 dependencies {
     implementation("io.ktor:ktor-server-cio:$ktorVersion")
     implementation("io.ktor:ktor-client-cio:$ktorVersion")
     implementation("io.ktor:ktor-client-apache:$ktorVersion")
     implementation("io.ktor:ktor-client-jackson:$ktorVersion")
     implementation("io.ktor:ktor-jackson:$ktorVersion")
     implementation("io.ktor:ktor-server-netty:$ktorVersion")
     implementation("io.ktor:ktor-auth-jwt:$ktorVersion") { exclude(group = "junit") }

     implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
     implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
     implementation("ch.qos.logback:logback-classic:1.2.3")
 }

 application {
     mainClass.set("AppKt")
 }
