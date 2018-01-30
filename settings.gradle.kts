// Workaround to make JUnit Platform and Spring Boot Gradle Plugins available using the `plugins` DSL
// See https://github.com/junit-team/junit5/issues/768

// Native Gradle support for JUnit5 is coming: https://github.com/gradle/gradle/issues/828
// also, https://github.com/gradle/gradle/pull/3886

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.junit.platform.gradle.plugin") {
                useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
            } else if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
            }
        }
    }
}