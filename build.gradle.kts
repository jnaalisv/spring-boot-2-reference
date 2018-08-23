plugins {
    java
    //jacoco
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.0.M2"

    id("com.gorylenko.gradle-git-properties") version "1.4.21"
}

version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

springBoot {
    buildInfo()
}

dependencyManagement {
    dependencies {
        // JAXB (JSR 222) Standalone Implementation
        dependency ("javax.xml.bind:jaxb-api:2.3.0")
        dependency ("com.sun.xml.bind:jaxb-impl:2.3.0")
        dependency ("com.sun.xml.bind:jaxb-core:2.3.0")

        // JSR-925 Java Beans Activation Framework
        dependency ("javax.activation:activation:1.1.1")
    }
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework:spring-orm")
    compile("org.hibernate:hibernate-core")
    compile("com.zaxxer:HikariCP")
    runtime("com.h2database:h2")

    compile("net.ttddyy:datasource-proxy:1.4.9")

    runtime ("javax.xml.bind:jaxb-api")
    runtime ("com.sun.xml.bind:jaxb-impl")
    runtime ("com.sun.xml.bind:jaxb-core")
    runtime ("javax.activation:activation")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.junit.jupiter:junit-jupiter-api")

    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

val test: Test by tasks
test.useJUnitPlatform()

//val jacocoTestReport: JacocoReport by tasks
//jacocoTestReport.reports {
//    xml.isEnabled = false
//    html.isEnabled = true
//    html.destination = file("${buildDir}/jacocoHtml")
//}

//test.finalizedBy(jacocoTestReport)
