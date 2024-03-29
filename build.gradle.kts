plugins {
    java
    jacoco
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "3.0.0-SNAPSHOT"

    id("com.gorylenko.gradle-git-properties") version "1.4.21"
}

version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

springBoot {
    buildInfo()
}

val dsProxyVersion = "1.7"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework:spring-orm")
    implementation("org.hibernate:hibernate-core-jakarta")
    implementation("com.zaxxer:HikariCP")
    implementation("net.ttddyy:datasource-proxy:$dsProxyVersion")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

jacoco {
    toolVersion = "0.8.7"
}

tasks {
    jacocoTestReport {
        reports {
            xml.isEnabled = false
            html.isEnabled = true
            html.destination = file("$buildDir/reports/tests/jacoco")
        }
    }

    test {
        //jvmArgs = listOf("--enable-preview")
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.apply {
        encoding = "UTF-8"
        compilerArgs.add("-Xlint:all")
        //compilerArgs.add("--enable-preview")
    }
}
