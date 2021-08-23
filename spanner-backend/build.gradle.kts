import java.nio.file.Paths

val ktorVersion = "1.6.1"
val jacksonVersion = "2.12.4"
val junitJupiterVersion = "5.6.2"
val tokenValidatorVersion = "1.3.8"

plugins {
     application
 }

 kotlin {
     jvm {
         withJava()
     }
     sourceSets {
         val jvmMain by getting {
            dependencies {

            }
         }
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
     implementation("no.nav.security:token-validation-ktor:$tokenValidatorVersion")
     implementation("io.ktor:ktor-server-sessions:$ktorVersion")
     implementation("io.ktor:ktor-auth:$ktorVersion")
     implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
     implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

     implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
     implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

     implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")

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
     testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
 }

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    named<ProcessResources>("jvmProcessResources") {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

    named<ProcessResources>("jvmTestProcessResources") {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

    named<Jar>("jvmJar") {
        duplicatesStrategy = DuplicatesStrategy.WARN
        mustRunAfter(clean, ":spanner-frontend:jsBrowserProductionWebpack")

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.spanner.AppKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }

        from({ Paths.get(project(":spanner-frontend").buildDir.path, "distributions") }){
            into("static")
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
     mainClass.set("no.nav.spanner.AppKt")
     applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
 }

