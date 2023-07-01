val ktor_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.1"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
}

group = "com.marketai"
version = "0.0.1"
application {
    mainClass.set("com.marketai.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
tasks {
    create("stage").dependsOn("installDist")
}

tasks.withType<Jar> {
    from("resources") {
        include("*.json")
    }
}
repositories {
    mavenCentral()
    mavenLocal()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }

}

dependencies {
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-sessions-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-auth:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    implementation ("com.google.code.gson:gson:2.8.8")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
// SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")
   // implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")

}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
