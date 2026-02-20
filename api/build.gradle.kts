import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
}

group = "com.prap"
version = "0.0.1-SNAPSHOT"
description = "Prap project for scraper api"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

fun openFile(path: String) {
    val os = org.gradle.internal.os.OperatingSystem.current()

    when {
        os.isWindows -> Runtime.getRuntime().exec(arrayOf("cmd", "/c", "start", path))
        os.isMacOsX -> Runtime.getRuntime().exec(arrayOf("open", path))
        else -> Runtime.getRuntime().exec(arrayOf("xdg-open", path))
    }
}

tasks.test {
    finalizedBy("jacocoTestReport", "openTestReport")
}

tasks.jacocoTestReport {
    finalizedBy("openJacocoReport")
}

tasks.register("openTestReport") {
    doLast {
        val reportFile = layout.buildDirectory
            .file("reports/tests/test/index.html")
            .get()
            .asFile

        openFile(reportFile.absolutePath)
    }
}

tasks.register("openJacocoReport") {
    doLast {
        val reportFile = layout.buildDirectory
            .file("reports/jacoco/test/html/index.html")
            .get()
            .asFile

        openFile(reportFile.absolutePath)
    }
}