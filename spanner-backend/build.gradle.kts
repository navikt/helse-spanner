import java.nio.file.Paths

val jacksonVersion = "2.12.5"
val junitJupiterVersion = "5.7.2"
val ktorVersion = "1.6.3"
val tokenValidatorVersion = "1.3.8"

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":spanner-common"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("no.nav.security:token-validation-ktor:$tokenValidatorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("com.papertrailapp:logback-syslog4j:1.0.0")
    implementation("com.natpryce:konfig:1.6.10.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("ch.qos.logback:logback-classic:1.2.5")
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
    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "16"
    }


    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    jar {
        mustRunAfter(clean, ":spanner-frontend:jsBrowserProductionWebpack")
        mustRunAfter(clean, ":frontend:npm_run_build")
        //mustRunAfter(clean, ":spanner-react:browserProductionWebpack")

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.spanner.AppKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }

        from({ Paths.get(project(":spanner-frontend").buildDir.path, "distributions") }) {
            //from({ Paths.get(project(":spanner-react").buildDir.path, "distributions") }) {
            into("static")
        }

        from({ Paths.get(project(":frontend").buildDir.path) }) {
            //from({ Paths.get(project(":spanner-react").buildDir.path, "distributions") }) {
            into("static/react")
        }

        from({ Paths.get(project(":frontend").buildDir.path, "assets") }) {
            //from({ Paths.get(project(":spanner-react").buildDir.path, "distributions") }) {
            into("static/assets")
        }

        doLast {
            configurations.runtimeClasspath.get()
                .filter { it.name != "app.jar" }
                .forEach {
                    val file = File("$buildDir/libs/${it.name}")
                    if (!file.exists())
                        it.copyTo(file)
                }
        }
    }
}

