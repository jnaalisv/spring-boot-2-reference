buildscript {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.0.M7")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }
}

apply {
    plugin("org.springframework.boot")

    // Native Gradle support for JUnit5 is coming: https://github.com/gradle/gradle/issues/828
    // also, https://github.com/gradle/gradle/pull/3886
    plugin("org.junit.platform.gradle.plugin")
}

plugins {
    java
    jacoco
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
}

version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("http://repo.spring.io/milestone")
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