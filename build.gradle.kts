plugins {
    java
    id("io.qameta.allure") version "3.0.0"
}

group = "automationexercise.com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Allure settings
allure {
    report {
        version.set("2.30.0")
    }
    adapter {
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.30.0")
            }
        }
    }
}

dependencies {
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.0")
    testImplementation("com.microsoft.playwright:playwright:1.55.0")

    // Hamcrest for assert
    testImplementation("org.hamcrest:hamcrest:3.0")

    // Allure report
    testImplementation("io.qameta.allure:allure-junit5:2.30.0")
    testImplementation("ch.qos.logback:logback-classic:1.5.19")

    // JSON parsing
    testImplementation("org.json:json:20240303")
}

tasks.test {
    useJUnitPlatform()

    maxParallelForks = 2
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
    systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
    systemProperty("allure.results.directory", "build/allure-results")
}
