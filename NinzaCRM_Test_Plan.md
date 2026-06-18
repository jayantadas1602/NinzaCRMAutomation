# Test Plan — Ninza CRM Automation Framework

**Document Title:** Ninza CRM Automation Test Plan  
**Version:** 1.0  
**Project:** NinzaCRMAutomation  
**Prepared By:** Jayanta Das  
**Date:** June 2026  
**Status:** Final

---

## Table of Contents

1. [Introduction](#1-introduction)
2. [Scope of Testing](#2-scope-of-testing)
3. [Test Objectives](#3-test-objectives)
4. [Test Environment](#4-test-environment)
5. [Framework Architecture](#5-framework-architecture)
6. [Test Suites](#6-test-suites)
7. [Modules and Test Cases](#7-modules-and-test-cases)
8. [Entry and Exit Criteria](#8-entry-and-exit-criteria)
9. [Test Execution Strategy](#9-test-execution-strategy)
10. [Test Reporting](#10-test-reporting)
11. [Defect Management](#11-defect-management)
12. [Risks and Mitigations](#12-risks-and-mitigations)
13. [Dependencies](#13-dependencies)

---

## 1. Introduction

This document defines the test plan for the **Ninza CRM Automation Framework** — a Selenium WebDriver-based automation suite developed to validate the functional behaviour of the Ninza CRM web application.

The framework is implemented in **Java** using the **TestNG** testing framework, **Maven** for build management, the **Page Object Model (POM)** design pattern for maintainability, and **ExtentReports** for rich HTML reporting. It supports smoke testing, regression testing, cross-browser testing, and parallel execution through configurable TestNG suite XML files.

---

## 2. Scope of Testing

### 2.1 In Scope

- Functional UI automation of the **Products** and **Campaigns** modules
- Smoke test coverage for critical happy-path scenarios
- Regression test coverage for all automated test cases
- Cross-browser testing on **Google Chrome** and **Microsoft Edge**
- Parallel and distributed test execution
- Data-driven testing using **Apache POI** and Excel input files
- Automatic screenshot capture on test failure
- HTML test reporting via **ExtentReports**

### 2.2 Out of Scope

- Performance / load testing
- API / backend testing
- Security testing
- Mobile browser testing
- Modules not yet automated: Contacts, Deals, Reports

---

## 3. Test Objectives

- Validate core CRUD operations for the Products and Campaigns modules
- Detect functional regressions early through automated regression execution
- Verify consistent UI behaviour across Chrome and Edge browsers
- Reduce manual testing effort for repeatable test scenarios
- Generate clear, stakeholder-readable HTML reports after every test run

---

## 4. Test Environment

| Component | Details |
|---|---|
| Application Under Test | Ninza CRM (Web Application) |
| Language | Java |
| Automation Library | Selenium WebDriver 4.35.0 |
| Test Framework | TestNG 7.8.0 |
| Build Tool | Apache Maven |
| IDE | Eclipse IDE |
| Reporting | ExtentReports 5.1.2 |
| Data Handling | Apache POI 4.1.2 (Excel read/write) |
| Database | MySQL (mysql-connector-j 8.4.0) |
| Browsers | Google Chrome (latest), Microsoft Edge (latest) |
| Operating System | Windows 10 / 11 |

---

## 5. Framework Architecture

### 5.1 Design Pattern — Page Object Model (POM)

Each CRM module is represented by a dedicated **Page class** that encapsulates all web element locators (using `@FindBy` annotations) and associated action methods. Test classes contain only test logic and call Page class methods — there is no raw Selenium code in test files.

This separation ensures that if the UI changes, only the Page class needs updating, not every individual test.

### 5.2 Core Components

| Component | Class / File | Responsibility |
|---|---|---|
| Base Class | `BaseClass.java` | WebDriver init/teardown, browser configuration, implicit/explicit waits |
| Listener Class | `ListenerClass.java` | Implements `ITestListener`; hooks into test lifecycle for automatic Extent Report generation |
| Page Objects | `ProductPage.java`, `CampaignPage.java` | Web element locators and reusable action methods per module |
| Test Classes | `ProductTest.java`, `CampaignTest.java` | TestNG `@Test` methods with group tagging (`smoke` / `regression`) |
| Excel Utility | `ExcelUtility.java` | Apache POI wrapper for reading test data from `.xlsx` files |
| TestNG Suites | `*.xml` files | Configure suite execution — groups, parallelism, browser parameters |

### 5.3 Project Structure

```
NinzaCRMAutomation/
├── src/
│   └── test/
│       └── java/
│           ├── base/
│           │   └── BaseClass.java
│           ├── listeners/
│           │   └── ListenerClass.java
│           ├── utilities/
│           │   └── ExcelUtility.java
│           ├── Product/
│           │   ├── ProductPage.java
│           │   └── ProductTest.java
│           └── Campaign/
│               ├── CampaignPage.java
│               └── CampaignTest.java
├── AdvanceReports/                  # ExtentReport HTML output
├── Screenshots/                     # Auto-captured failure screenshots
├── BatchSuite.xml
├── RegressionSuite.xml
├── SmokeSuite.xml
├── CrossBrowser.xml
├── DistributedParallel.xml
└── pom.xml
```

---

## 6. Test Suites

| Suite File | Groups Executed | Purpose |
|---|---|---|
| `BatchSuite.xml` | All | Runs all tests across both modules with no group filter. Used for full validation. |
| `RegressionSuite.xml` | `regression` | Executes all `@Test(groups="regression")` tests. Intended for post-build regression runs. |
| `SmokeSuite.xml` | `smoke` | Executes only `@Test(groups="smoke")` tests — critical path checks for quick deployment validation. |
| `CrossBrowser.xml` | All (parametrised) | Runs CampaignTest in parallel on Chrome and Edge using TestNG `@Parameters`. |
| `DistributedParallel.xml` | All | Distributed parallel execution to run tests simultaneously across threads. |

---

## 7. Modules and Test Cases

### 7.1 Products Module

**Package:** `Product` | **Page Class:** `ProductPage.java` | **Test Class:** `ProductTest.java`

| TC ID | Test Case Name | Group(s) | Priority | Expected Result | Steps Summary |
|---|---|---|---|---|---|
| TC-P-01 | Add New Product | smoke, regression | High | Product created successfully | Login → Products → Add → Fill form → Submit → Verify success message |
| TC-P-02 | Edit Existing Product | regression | High | Product updated successfully | Login → Products → Select → Edit → Modify fields → Save → Verify changes |
| TC-P-03 | Delete Product | regression | Medium | Product removed from listing | Login → Products → Select → Delete → Confirm → Verify removed |
| TC-P-04 | Verify Product Listing | smoke | Medium | Products grid loads with correct data | Login → Products → Verify listing table displays with correct columns |

---

### 7.2 Campaigns Module

**Package:** `Campaign` | **Page Class:** `CampaignPage.java` | **Test Class:** `CampaignTest.java`

| TC ID | Test Case Name | Group(s) | Priority | Expected Result | Steps Summary |
|---|---|---|---|---|---|
| TC-C-01 | Create Campaign | smoke, regression | High | Campaign created successfully | Login → Campaigns → New → Fill required fields → Save → Verify confirmation |
| TC-C-02 | Edit Campaign | regression | High | Campaign updated successfully | Login → Campaigns → Select → Edit → Update fields → Save → Verify changes |
| TC-C-03 | Delete Campaign | regression | Medium | Campaign removed from listing | Login → Campaigns → Select → Delete → Confirm → Verify removed |
| TC-C-04 | Campaign Cross-Browser — Chrome | regression | High | Consistent behaviour on Chrome | CrossBrowser.xml: Run CampaignTest with `BROWSER=Chrome` parameter |
| TC-C-05 | Campaign Cross-Browser — Edge | regression | High | Consistent behaviour on Edge | CrossBrowser.xml: Run CampaignTest with `BROWSER=Edge` parameter |

---

## 8. Entry and Exit Criteria

### 8.1 Entry Criteria

- Application build is deployed and accessible in the test environment
- Test data Excel files are prepared and placed in the correct directory
- All Maven dependencies resolve without errors (`mvn clean install`)
- Chrome and Edge browsers are installed with compatible WebDriver versions
- TestNG plugin is installed in Eclipse IDE

### 8.2 Exit Criteria

- All automated test cases in the active suite have been executed
- Pass rate is 100% for smoke tests and at least 95% for regression tests
- All Critical (P1) and High (P2) test failures have been investigated and logged
- ExtentReport HTML has been generated and reviewed
- Failure screenshots have been captured for all failed test cases

---

## 9. Test Execution Strategy

### 9.1 Maven Build Profiles

| Profile ID | Maven Command | Suite Executed |
|---|---|---|
| `SS` | `mvn test -P SS` | `SmokeSuite.xml` — fast smoke check on critical paths |
| `RS` | `mvn test -P RS` | `RegressionSuite.xml` — full regression coverage |

### 9.2 Direct Suite Execution via CLI

Run any suite file directly without a Maven profile:

```bash
mvn test -Dsurefire.suiteXmlFiles=CrossBrowser.xml
mvn test -Dsurefire.suiteXmlFiles=BatchSuite.xml
mvn test -Dsurefire.suiteXmlFiles=DistributedParallel.xml
```

### 9.3 Eclipse Execution

Right-click any `.xml` suite file in Eclipse's Package Explorer → **Run As → TestNG Suite**

### 9.4 Execution Order

```
Smoke Suite (quick gate check)
        ↓
Regression Suite (full coverage)
        ↓
Cross-Browser Suite (browser compatibility)
        ↓
Review ExtentReport + Screenshots
```

---

## 10. Test Reporting

### 10.1 ExtentReports HTML Report

An HTML report is automatically generated after every test run at:

```
AdvanceReports/ExtentReport.html
```

The report includes:

- Suite-level pass / fail / skip counts
- Step-by-step logs for each test method
- Embedded failure screenshots
- Start time, end time, and total execution duration
- Environment metadata (browser, OS)

### 10.2 Failure Screenshots

Screenshots are captured automatically on test failure via `ListenerClass.java` and stored at:

```
Screenshots/<TestName>_<timestamp>.png
```

These are also embedded directly in the ExtentReport for inline viewing.

---

## 11. Defect Management

| Severity | Priority | Description |
|---|---|---|
| Critical (S1) | P1 | Application crash, login failure, complete module unavailability |
| High (S2) | P2 | Core CRUD operations failing, data not saved, incorrect data displayed |
| Medium (S3) | P3 | Non-critical UI issues, minor validation failures, cosmetic inconsistencies |
| Low (S4) | P4 | Minor UX issues, typos, non-blocking visual discrepancies |

All defects identified during automation runs are to be logged with:
- TC ID and test method name
- Steps to reproduce
- Expected vs actual result
- Screenshot (auto-attached from the `Screenshots/` folder)
- ExtentReport link for the failed run

---

## 12. Risks and Mitigations

| Risk | Likelihood | Mitigation |
|---|---|---|
| WebDriver / browser version mismatch | Medium | Selenium 4 includes built-in driver management; update ChromeDriver regularly |
| Test data staleness in Excel files | Low | Maintain a dedicated test data sheet; version-control Excel files in the repo |
| Dynamic locators breaking after UI changes | Medium | Use stable locator strategies (IDs, `data-*` attributes); review on each sprint |
| Flaky tests due to timing issues | Medium | Use explicit `WebDriverWait` over implicit waits; add retry logic for known flaky tests |
| Test environment unavailability | Low | Coordinate schedules with environment owners; add health-check as a precondition step |

---

## 13. Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| `selenium-java` | 4.35.0 | Core WebDriver API for browser automation |
| `testng` | 7.8.0 | Test execution framework with grouping, parallel, and listener support |
| `extentreports` | 5.1.2 | Rich HTML report generation |
| `poi` + `poi-ooxml` | 4.1.2 | Read/write Excel `.xlsx` files for data-driven testing |
| `mysql-connector-j` | 8.4.0 | MySQL JDBC driver for database validation scenarios |
| `maven-surefire-plugin` | 3.5.5 | Maven plugin to run TestNG suite files via build profiles |

---

*Document prepared by **Jayanta Das** — QA Test Engineer*  
*GitHub: [jayantadas1602/NinzaCRMAutomation](https://github.com/jayantadas1602/NinzaCRMAutomation)*
