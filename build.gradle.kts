plugins {
    java
    jacoco
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
    id("org.junit.platform.gradle.plugin") version "1.1.0"
    id("org.springframework.boot") version "2.0.0.RELEASE"
}

version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testCompile("org.junit.jupiter:junit-jupiter-api")

    // To avoid compiler warnings about @API annotations in JUnit code
    testCompileOnly("org.apiguardian:apiguardian-api:1.0.0")

    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

val junitPlatformTest: JavaExec by tasks

jacoco {
    applyTo(junitPlatformTest)
}

val main: SourceSet by java.sourceSets

task<JacocoReport>("junit5CoverageReport") {
    executionData(junitPlatformTest)
    sourceSets(main)

    reports {
        xml.isEnabled = false
        html.isEnabled = true
        html.destination = file("${buildDir}/jacocoHtml")
    }

    junitPlatformTest.finalizedBy(this)
}
