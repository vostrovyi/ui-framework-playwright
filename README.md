## Tech Stack

* **Language:** Java 17
* **Core Framework:** [Playwright for Java](https://playwright.dev/java/)
* **Build Tool:** Gradle (Kotlin DSL)
* **Test Runner:** JUnit 5
* **Assertions:** Hamcrest
* **Reporting:** Allure Report
* **JSON Parsing:** org.json
* **Architecture:** Controller / Facade Pattern

## Prerequisites

Before running the tests, ensure you have the following installed:

1. **Java JDK 17** or higher.
2. **Allure CLI** (Required to view reports locally).
    * MacOS: `brew install allure`
    * Windows: `scoop install allure`

## How to Run

The project uses Gradle Wrapper, so you don't need to manually install Gradle.

### 1. Run all tests

This command runs all tests in parallel (configured for 2 threads).

```bash
./gradlew clean test
```

### Run specific tests

```bash
./gradlew clean test --tests "automationexercise.tests.LoginTest"
```

## Reporting

```bash
allure serve build/allure-results
```
