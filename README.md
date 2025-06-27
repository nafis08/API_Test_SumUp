# 🏦 Global Bank API Test Suite

This repository provides an automated test suite for the Global Bank Account Creation service. It is designed to support QA engineers with reusable, maintainable, and configurable tools that streamline validation of the bank’s core account functionality.

---

## 🔍 System Under Test

**Base URL:**

```
https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net
```

**Endpoints:**

- `POST /api/accounts`: Create a new bank account.
- `GET /api/accounts/:id`: Poll for account status and retrieve the IBAN.

**API Documentation:**  
[Swagger UI](https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net/swagger-ui/index.html)

---

## 📦 Tech Stack

- **Language:** Java
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Reporting:** Allure
- **Environment Management:** `.env` file
- **IDE Support:** Eclipse

---

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6 or newer
- Allure CLI (for generating HTML reports)
- Internet access to call the external API
- Optional: Eclipse IDE (project uses `.project` and `.classpath`)

---

## ▶️ Running the Tests

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd API_Test_SumUp-main
```

### 2. Set Environment Variables

Create or edit a `.env` file at the project root:

```
BASE_URL=https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net
API_KEY=your_api_key_here
```

Or export them directly (for Linux/macOS):

```bash
export BASE_URL=https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net
export API_KEY=your_api_key_here
```

### 3. Run Tests

```bash
mvn clean test
```

### 4. Generate and View the Report

```bash
allure serve allure-results
```

---

## 📁 Project Structure

```
API_Test_SumUp-main/
├── src/
│   ├── main/java/
│   └── test/java/
│       └── com/sumup/tests/
├── .env
├── pom.xml
├── testng.xml
├── allure-report/
└── README.md
```

---

## ✅ Test Coverage

| Test Scenario | Description |
|---------------|-------------|
| ✔️ Valid account creation | Ensures all required fields result in successful account |
| ✔️ Underage client validation | Prevents account creation for clients under 18 |
| ✔️ Name length check | Validates name length does not exceed 50 characters |
| ✔️ Initial deposit check | Ensures deposit is correctly recorded |

---

## ⚠️ Known Bugs & Risks

| ID | Description | Severity | Status |
|----|-------------|----------|--------|
| 1  | Accepting null values as userName | High | In Progress |
| 2  | Error message for underage client is unclear | Medium | Reported |
| 3  | Accepts SQL and Script injection | Low | Confirmed |

---

## 🔧 TODOs

- [ ] Add DataProviders for parameterized test inputs
- [ ] Implement retry logic for IBAN polling
- [ ] Add mock API support for edge case testing
- [ ] Improve error response parsing and assertions
- [ ] Support environment switching (local/staging/prod)
- [ ] Implement BDD for better understanding of the test cases

