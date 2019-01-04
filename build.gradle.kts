plugins {
    java
    jacoco
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.1.RELEASE"

    id("com.gorylenko.gradle-git-properties") version "1.4.21"
}

version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

springBoot {
    buildInfo()
}

val jaxbVersion = "2.3.1"
val dsProxyVersion = "1.5"

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework:spring-orm")
    compile("org.hibernate:hibernate-core")
    compile("com.zaxxer:HikariCP")
    runtime("com.h2database:h2")

    compile("net.ttddyy:datasource-proxy:$dsProxyVersion")

    // JAXB (JSR 222) Standalone Implementation
    runtime ("javax.xml.bind:jaxb-api:$jaxbVersion")
    runtime ("org.glassfish.jaxb:jaxb-runtime:$jaxbVersion")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.junit.jupiter:junit-jupiter-api")

    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    jacocoTestReport {
        reports {
            xml.isEnabled = false
            html.isEnabled = true
            html.destination = file("${buildDir}/reports/tests/jacoco")
        }
    }

    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }
}
