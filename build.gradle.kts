plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.ilyankin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("com.ilyankin.minesweeper.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}