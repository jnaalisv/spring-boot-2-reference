plugins {
    java
    jacoco
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
    id("org.springframework.boot") version "2.0.2.RELEASE"
}

version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

springBoot {
    buildInfo()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework:spring-orm")
    compile("org.hibernate:hibernate-core")
    compile("com.zaxxer:HikariCP")
    compile("com.h2database:h2")

    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testCompile("org.junit.jupiter:junit-jupiter-api")

    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

val test: Test by tasks
test.useJUnitPlatform()

val jacocoTestReport: JacocoReport by tasks
jacocoTestReport.reports {
    xml.isEnabled = false
    html.isEnabled = true
    html.destination = file("${buildDir}/jacocoHtml")
}

test.finalizedBy(jacocoTestReport)
