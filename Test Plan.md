# Megaport API Testing Plan

## 1. Overview

This test plan outlines the strategy for testing the Megaport Location API. The tests focus on validating the API's ability to correctly filter and return location data based on various parameters.

## 2. Test Objectives

- Verify the Locations API returns correct data when no filters are applied
- Validate filtering functionality by various parameters (status, metro, vendor)
- Ensure combined filtering works correctly with multiple parameters
- Verify API response structure and content is consistent with expected format

## 3. Test Scope

### In Scope

- `/locations` endpoint functionality
- Parameter filtering (status, metro, vendor, combined filters)
- Response validation for all filter combinations
- Error handling and validation of response content

### Out of Scope

- Authentication and authorization testing
- Performance and load testing
- UI/Frontend integration testing
- Database integrity validation

## 4. Test Approach

We're using a data-driven approach with TestNG data providers to systematically test various parameter combinations. The approach includes:

- Individual parameter tests: Testing each filter parameter in isolation
- Combined parameter tests: Testing combinations of parameters
- Response validation: Ensuring returned data matches the filter criteria

## 5. Test Scenarios

### 5.1 Basic API Tests

| Test ID | Description | Priority |
|---------|-------------|----------|
| GET-01 | Retrieve all public locations without filters | Blocker |

### 5.2 Single Parameter Filter Tests

| Test ID | Description | Priority |
|---------|-------------|----------|
| STATUS-01 | Filter locations by status (multiple values via data provider) | Normal |
| METRO-01 | Filter locations by metro (multiple values via data provider) | Normal |
| VENDOR-01 | Filter locations by vendor (multiple values via data provider) | Normal |

### 5.3 Combined Parameter Tests

| Test ID | Description | Priority |
|---------|-------------|----------|
| COMBO-01 | Filter by combination of vendor, status, and metro | Critical |


## 6. Validation Strategy

Each test validates:
- Response structure (status code, message, terms)
- Data integrity (non-null values for critical fields)
- Filter accuracy (returned locations match the requested filter criteria)

## 7. Test Environment

- Environment: Staging API (`api-staging.megaport.com`)
- Data requirements: Existing location data with various statuses, metros, and vendors
- Configuration: Connection details stored in config properties

## 8. Test Execution Strategy

- Tests can be run individually or as a suite
- All tests are independent and can be executed in any order
- Data providers ensure comprehensive coverage without test duplication
- Allure reporting provides detailed test results with parameter values

## 9. Reporting

Tests use Allure for reporting:
- Test results include parameters used for each test run
- Failures include detailed error messages and affected data
- Reports show test distribution across different filter combinations

## 10. Future Enhancements

- Add tests for market-enabled filtering once functionality is clarified
- Add negative testing scenarios
- Consider performance testing for large result sets 