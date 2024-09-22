import org.gradle.kotlin.dsl.attributes

plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.camilo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenCentral()
    mavenLocal()
    maven(url = "https://repo.spring.io/snapshot")
    maven(url = "https://repo.spring.io/milestone")
    maven(url = "https://repo.spring.io/release")  
     maven {
      url = uri("https://plugins.gradle.org/m2/")
    }

}

val arrowKtVersion = "1.2.4"
dependencies {
    implementation("io.arrow-kt:arrow-core:$arrowKtVersion")
    implementation("io.arrow-kt:arrow-fx-coroutines:$arrowKtVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("ch.qos.logback:logback-classic:1.4.12")

    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
    testImplementation("io.kotest:kotest-assertions-json:5.9.0")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}


tasks.withType<Jar> {
    manifest {
        attributes("Main-Class" to "com.camilo.Main")
    }
}
application {
    mainClass.set("com.camilo.Main")
}
