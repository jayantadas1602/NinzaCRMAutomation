# NinzaCRM Automation Framework

A production-ready Selenium WebDriver automation framework for **Ninza CRM** — a web-based Customer Relationship Management application. Built with Java, TestNG, and Maven, implementing the Page Object Model (POM) design pattern with Extent Reports for rich HTML reporting.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Framework Architecture](#framework-architecture)
- [Modules Automated](#modules-automated)
- [Test Suites](#test-suites)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Running the Tests](#running-the-tests)
- [Test Reports](#test-reports)
- [Key Framework Features](#key-framework-features)
- [Author](#author)

---

## Project Overview

This framework automates end-to-end functional testing for the Ninza CRM application. It covers core CRM modules — Products and Campaigns — using a structured, maintainable architecture that supports smoke testing, regression testing, cross-browser testing, and parallel execution.

---

## Tech Stack

| Component | Technology / Version |
|---|---|
| Language | Java |
| Build Tool | Maven |
| Test Framework | TestNG 7.8.0 |
| Automation Library | Selenium WebDriver 4.35.0 |
| Reporting | ExtentReports 5.1.2 |
| Data Handling | Apache POI 4.1.2 (Excel) |
| Database | MySQL Connector/J 8.4.0 |
| IDE | Eclipse |
| Browser Support | Chrome, Microsoft Edge |

---

## Project Structure

```
NinzaCRMAutomation/
├── src/
│   └── test/
│       └── java/
│           ├── base/
│           │   └── BaseClass.java          # WebDriver init, teardown, browser config
│           ├── listeners/
│           │   └── ListenerClass.java      # TestNG ITestListener for Extent Reports
│           ├── utilities/
│           │   └── ExcelUtility.java       # Apache POI-based data reader
│           ├── Product/
│           │   ├── ProductPage.java        # Page Object for Products module
│           │   └── ProductTest.java        # Test class for Products
│           └── Campaign/
│               ├── CampaignPage.java       # Page Object for Campaigns module
│               └── CampaignTest.java       # Test class for Campaigns
├── AdvanceReports/                         # Extent Report HTML output
├── Screenshots/                            # Failure screenshots (auto-captured)
├── BatchSuite.xml                          # Run all tests (no grouping)
├── RegressionSuite.xml                     # Run @Test(groups="regression") tests
├── SmokeSuite.xml                          # Run @Test(groups="smoke") tests
├── CrossBrowser.xml                        # Parallel Chrome + Edge execution
├── DistributedParallel.xml                 # Distributed parallel execution config
└── pom.xml                                 # Maven dependencies & build profiles
```

---

## Framework Architecture

### Page Object Model (POM)
Each CRM module has a dedicated Page class containing all web element locators and reusable action methods. Test classes only call these methods — no raw Selenium code in test files.

### Base Class
`BaseClass.java` handles WebDriver initialization, browser configuration (Chrome/Edge), implicit/explicit waits, and teardown. All test classes extend `BaseClass`.

### Listener Class
`ListenerClass.java` implements TestNG's `ITestListener` interface to hook into the test lifecycle — logging test start/pass/fail events and generating Extent Report entries automatically without needing to add reporting code inside each test.

### Data-Driven Testing
`ExcelUtility.java` uses Apache POI to read test data from `.xlsx` files, enabling parameterized test execution without hardcoding values in test methods.

---

## Modules Automated

### Products Module
- Add new product
- Edit product details
- Delete product
- Verify product listing

### Campaigns Module
- Create campaign
- Edit campaign
- Delete campaign
- Cross-browser campaign verification (Chrome & Edge)

---

## Test Suites

| Suite File | Purpose | Groups Executed |
|---|---|---|
| `BatchSuite.xml` | Run all tests without group filter | All |
| `RegressionSuite.xml` | Full regression pass | `regression` |
| `SmokeSuite.xml` | Quick smoke check | `smoke` |
| `CrossBrowser.xml` | Parallel Chrome + Edge | All (parametrized) |
| `DistributedParallel.xml` | Distributed parallel execution | All |

---

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Eclipse IDE (with TestNG plugin installed)
- Google Chrome browser
- Microsoft Edge browser
- Git

---

## Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/jayantadas1602/NinzaCRMAutomation.git
   cd NinzaCRMAutomation
   ```

2. **Open in Eclipse**
   - File → Import → Existing Maven Projects
   - Browse to the cloned directory and click Finish

3. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

4. **Verify TestNG plugin**  
   In Eclipse: Help → Eclipse Marketplace → search "TestNG" and install if not present.

---

## Running the Tests

### From Eclipse (Right-click)
Right-click any `.xml` suite file → Run As → TestNG Suite

### From Maven CLI

```bash
# Run Smoke Suite
mvn test -P SS

# Run Regression Suite
mvn test -P RS

# Run a specific suite directly
mvn test -Dsurefire.suiteXmlFiles=CrossBrowser.xml
```

### Run All Tests (no profile)
```bash
mvn test -Dsurefire.suiteXmlFiles=BatchSuite.xml
```

---

## Test Reports

After every test run, an HTML report is generated under:

```
AdvanceReports/
└── ExtentReport.html
```

Open `ExtentReport.html` in any browser. The report includes:
- Pass/Fail/Skip counts per suite
- Step-by-step logs for each test
- Failure screenshots embedded inline
- Execution timestamps and duration

---

## Key Framework Features

- **Page Object Model** — clean separation of locators and test logic
- **BaseClass** — centralized driver setup/teardown, no boilerplate in tests
- **ITestListener** — automatic Extent Report generation, zero effort per test
- **Cross-Browser Testing** — Chrome and Edge via `CrossBrowser.xml`
- **Parallel Execution** — simultaneous test runs via `DistributedParallel.xml`
- **Data-Driven Testing** — Apache POI reads test data from Excel
- **Auto Screenshots on Failure** — captured and saved to `/Screenshots`
- **Maven Profiles** — `RS` for regression, `SS` for smoke; run from CI/CD easily
- **MySQL Integration** — DB connector available for database validation tests

---

## Author

**Jayanta Das**  
QA Test Engineer | Selenium | Java | TestNG | Maven  
[GitHub Profile](https://github.com/jayantadas1602)
