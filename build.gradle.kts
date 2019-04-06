plugins {
    java
    jacoco
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.4.RELEASE"

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

val dsProxyVersion = "1.5"
ext["hibernate.version"] = "5.4.2.Final"
ext["hikaricp.version"] = "3.3.1"
ext["junit-jupiter.version"] = "5.4.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework:spring-orm")
    implementation("org.hibernate:hibernate-core")
    implementation("com.zaxxer:HikariCP")
    implementation("net.ttddyy:datasource-proxy:$dsProxyVersion")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
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
