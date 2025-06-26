
# 🧪 API Automation Framework (RestAssured + TestNG + GitHub Actions)

This project is a fully functional API Test Automation Framework designed to demonstrate professional-level skills in testing RESTful APIs using **Java**, **RestAssured**, **TestNG**, and **GitHub Actions**.

---

## 🚀 Project Features

- ✅ **Thread-safe execution** using `ThreadLocal` for request specifications and authentication tokens.
- ✅ **Parallel testing** with TestNG's `parallel="methods"` configuration.
- ✅ **Clean architecture** following the Service Object Model (SOM) pattern.
- ✅ **CI Integration** with GitHub Actions for automated test execution on push.
- ✅ **Dynamic test data** with Faker to avoid data duplication issues.
- ✅ **Custom logging filter** for detailed request/response traceability.
- ✅ **Bug-detection ready**: tests exposed real backend issues (SQL injection, email case-sensitivity).

---

## 📘 API Under Test

This project tests the [Expand Testing Practice API](https://practice.expandtesting.com/notes/api/api-docs/#/), which includes endpoints for:

- User registration and login
- Authentication-protected profile access
- Note creation, editing, and deletion

The API allows realistic practice of token-based authentication, request/response validation, and common edge cases.

---

## 📂 Project Structure

```
src/
├── main/
│   └── java/
│       └── com.api/
│           ├── services/          # BaseService and specific endpoint services
│           ├── filters/           # Custom logging and listeners
│           └── utilities/         # ConfigReader
├── test/
│   └── java/
│       └── com.api.tests/         # Test classes
│
└── resources/
    └── suites/                    # TestNG XML suites (parallel, regression, etc.)
```

---

## 🛠️ Tools & Technologies

- Java 17
- Maven
- RestAssured
- TestNG
- Faker
- GitHub Actions

---

## 🧪 Running the Tests

### Run default suite (parallel):
```bash
mvn clean test -Dsuite=parallel
```


### Run without bug-prone tests:
```bash
mvn clean test -DexcludedGroups=knownBug
```

> TestNG groups allow isolation of unstable or bug-related test cases.

---

## 🤖 CI/CD with GitHub Actions

This project includes a working workflow in:
```
.github/workflows/ci.yml
```

CI is triggered on each push to the main/master branch and runs tests automatically.

---

## 🐞 Known Bugs Detected

These tests helped identify real-world issues in the tested API:
- 🔓 SQL Injection accepted in `name` field.
- 📬 Email validation rejects all-uppercase emails.
- 🔁 Duplicate registrations not handled gracefully.

Tests are marked accordingly and grouped as `"knownBug"` for easy exclusion if needed.

---

## 📌 How to Extend

- Add login automation via endpoint if backend allows it.
- Integrate reporting (e.g. Allure or Extent).
- Create smoke/regression/staging suites for more strategy control.
- Parameterize environments (dev, stage, prod) via Maven profiles.

---

## 👨‍💻 Author

Celeste May Herrera – Automation Engineer

---
