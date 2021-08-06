val ktorVersion = "1.6.1"
val jacksonVersion = "2.12.4"
val junitJupiterVersion = "5.6.2"

plugins {
     application
 }

 kotlin {
     jvm {
         withJava()
     }
     sourceSets {
         val jvmMain by getting {}
         val jvmTest by getting {}
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
     implementation("net.logstash.logback:logstash-logback-encoder:6.6") {
         exclude("com.fasterxml.jackson.core")
         exclude("com.fasterxml.jackson.dataformat")
     }

     implementation("io.jsonwebtoken:jjwt-api:0.11.2")
     runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
     runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

     testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
     testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
     testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

     testImplementation("no.nav.security:mock-oauth2-server:0.3.4")
 }

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    named<Jar>("jvmJar") {
        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "AppKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }

        doLast {
            configurations.runtimeClasspath.get().forEach {
                val file = File("$buildDir/libs/${it.name}")
                if (!file.exists())
                    it.copyTo(file)
            }
        }
    }
}

 application {
     mainClass.set("AppKt")
     applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
 }
