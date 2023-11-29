plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(15))
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation(kotlin("test"))
}
