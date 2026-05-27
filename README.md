# DemoQA Automation Framework

Advanced Selenium Java Automation Framework built on DemoQA, 
covering all advanced Selenium concepts using Page Object Model, 
TestNG, Maven and WebDriverManager.

---

## 🛠️ Tech Stack

| Tool | Version |
|---|---|
| Selenium | 4.18.1 |
| TestNG | 7.9.0 |
| Maven | Latest |
| WebDriverManager | 5.7.0 |
| Apache POI | 5.2.3 |
| Log4j | 2.23.1 |
| Java | 1.8 |

---
---

## 🧪 Concepts Covered

| # | Topic | Implementation |
|---|---|---|
| 1 | XPath Advanced | axes, contains(), normalize-space() |
| 2 | Actions Class | right-click, double-click, drag & drop |
| 3 | JavaScript Executor | scroll, click, highlight, form fill |
| 4 | Frames & iFrames | switch by ID, index, WebElement, nested |
| 5 | Multiple Windows & Alerts | tabs, windows, simple/confirm/prompt alerts |
| 6 | TestNG Advanced | Groups, Retry Analyzer, Listeners |
| 7 | Page Factory | @FindBy, initElements |
| 8 | Cross Browser Testing | Chrome, Firefox, Edge via parameters |
| 9 | Log4j Logging | INFO, DEBUG, ERROR with file output |
| 10 | Fluent Wait | polling, custom conditions, ignore exceptions |

---

## ✅ Test Results

| Test Class | Tests | Status |
|---|---|---|
| ActionsTest | 3 | ✅ All Passing |
| FramesTest | 4 | ✅ All Passing |
| WindowsAndAlertsTest | 5 | ✅ All Passing |
| TestNGAdvancedTest | 4 | ✅ All Passing |
| GoogleTest | 5 | ✅ All Passing |

---

## ⚙️ Key Features

- **Page Object Model** — clean separation of test and page logic
- **BaseTest** — common setup, teardown, auto screenshot on failure
- **Auto Screenshot** — captured on failure, deleted on pass
- **LocatorExtractor** — visits 6 DemoQA pages and exports all 
  element locators to Excel automatically
- **Log4j Logging** — timestamped logs saved to file
- **TestNG Groups** — smoke and regression test separation
- **Retry Analyzer** — auto reruns flaky tests up to 2 times
- **Test Listener** — real time pass/fail/skip console summary

---
