# Megaport API Test Suite

An automated test suite for validating the Megaport API functionality, with a focus on the Locations endpoint.

## Overview

This project contains automated tests for the Megaport API, ensuring that location data can be properly retrieved and filtered based on various parameters like vendor, status, and metro.

## Features

- Data-driven testing using TestNG data providers
- Comprehensive validation of API responses
- Detailed reporting using Allure
- Efficient test design with minimal code duplication
- Test plan documentation

## Prerequisites

- Java 8 or higher
- Maven
- Git
- Allure command-line tool

## Getting Started

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/megaport-api-tests.git
   cd megaport-api-tests
   ```

2. Configure environment:
   Update the configuration properties in `src/test/resources/config.properties`:
   ```properties
   api.base.url=https://api-staging.megaport.com/v2
   ```

### CI/CD with GitHub Actions

This project includes a GitHub Actions workflow that:

1. Runs all tests automatically on push to main/master branches or on pull requests
2. Generates Allure reports from test results
3. Publishes the reports to GitHub Pages
4. Maintains a history of reports for tracking test results over time

To access the published reports:
- Go to `https://[your-github-username].github.io/megaport-api-tests/`
- Reports are numbered by build run for historical tracking

To manually trigger the workflow:
1. Go to the "Actions" tab in your GitHub repository
2. Select "API Tests with Allure Report" workflow
3. Click "Run workflow"

**Note:** The first time you use GitHub Pages, you may need to enable it in your repository settings.

### Project Structure

```
├── src
│   └── test
│       ├── java
│       │   └── com
│       │       └── megaport
│       │           ├── api
│       │           │   └── v2
│       │           │       └── Locations.java    # API test implementation
│       │           ├── model
│       │           │   └── locations             # Model classes
│       │           │       ├── Location.java
│       │           │       ├── LocationResponse.java
│       │           │       ├── LocationStatus.java
│       │           │       ├── Vendor.java
│       │           │       └── ...
│       │           └── util                      # Utility classes
│       │               ├── Config.java
│       │               └── Reporting.java
│       └── resources
│           └── config.properties                 # Configuration
├── TEST_PLAN.md                                  # Test plan documentation
└── README.md                                     # This file
```

## Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Groups

```bash
# Run only vendor tests
mvn clean test -Dgroups=vendor

# Run only status tests
mvn clean test -Dgroups=status

# Run only metro tests
mvn clean test -Dgroups=metro
```

### Run with Specific Parameters

```bash
# Run with specific test class
mvn clean test -Dtest=Locations
```

## Test Approach

Our testing approach follows these principles:

1. **Data-driven testing**: Using TestNG data providers to run the same test with multiple parameter combinations
2. **Reusable validation**: Common validation logic is abstracted into reusable methods
3. **Clean test structure**: Each test is focused on validating a specific functionality
4. **Comprehensive reporting**: Detailed test reports to easily identify issues

For more details, see the [Test Plan](TEST_PLAN.md).

## Reports

After running tests, Allure reports are generated in the `allure-results` directory. To view the reports:

1. Install Allure command-line tool (if not already installed):
   ```bash
   npm install -g allure-commandline
   ```

2. Generate and open the report:
   ```bash
   allure serve allure-results
   ```