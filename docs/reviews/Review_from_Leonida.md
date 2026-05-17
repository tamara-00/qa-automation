# Review: Checkout, Error Handling, Shopping & User Account Tests

**Scope:** TC13-TC28 (Excluding Navigation and Search Filter)  


---

## Executive Summary

Reviewed 18 test cases across 4 categories. Found **12 critical issues**
---

## 1. CHECKOUT TESTS (TC18-TC23)

### Critical Issues
- **Brittle String Matching**: 22+ instances of `getPageSource().contains()` - tests pass despite broken UI
- **Missing Intermediate Assertions**: No verification between checkout steps
- **Incomplete Address Handling**: Address step skipped if missing, no flow verification

### Example Problem
```java
// ✗ WRONG - Catches footer text, not actual form
boolean shippingReached = driver.getPageSource().contains("shipping");

// ✓ CORRECT
boolean shippingReached = homePage.isShippingStepVisible();
```

### Major Issues
- Hard-coded test data scattered (5+ locations per value)
- Generic validation error matching (catches unrelated text)

---

## 2. ERROR HANDLING TESTS (TC33-TC36)

### Critical Issues
- **TC34 Incomplete**: Admits "can't test 0 and letter input values" - test doesn't validate what it claims
- **Meaningless Assertions**: `assertTrue(cart >= 0)` passes for any non-negative number (0, 5, 100 all pass)
- **Hard-coded Delays**: `Thread.sleep(3000)` in 4 tests (12+ seconds wasted per run)

### Example Problem
```java
// ✗ WRONG - Passes when cart = 0 (empty) or cart = 100 (corrupted)
assertTrue(cartAfterRefresh >= 0, "Cart should persist");

// ✓ CORRECT
assertEquals(cartBefore, cartAfterRefresh, "Cart count should be identical");
```

### Major Issues
- Generic page source assertions (12+ across tests)
- Debug output left in production code (TC35, TC36)

---

## 3. SHOPPING CART TESTS (TC13-TC17)

### Critical Issues
- **Hard-coded Magic Numbers**: TC14 increments by 4, expects 5 (assumes starting quantity = 1)
- **Integer Parsing Without Validation**: No null/empty checks before `Integer.parseInt()`
- **Exception Swallowing**: TC13 catches all exceptions, falls back to string matching

### Example Problem
```java
// ✗ WRONG - Crashes if badge is empty or non-numeric
int count = Integer.parseInt(productPage.getCartBadgeCount());

// ✓ CORRECT
String text = productPage.getCartBadgeCount().trim();
int count = text.isEmpty() ? 0 : Integer.parseInt(text.replaceAll("[^0-9]", ""));
```

### Major Issues
- Inconsistent wait strategies across tests
- Modal verification not consistent

---

## 4. USER ACCOUNT TESTS (TC24-TC28)

### Critical Issues
- **Hard-coded Shared Credentials**: `pub@prestashop.com` / `123456789` in multiple files
  - Security risk: credentials exposed in source control
  - Cannot test with different users
  - If credentials change, 6 locations must update

- **Redundant Frame Switching**: TC27, TC28 switch out then back in unnecessarily
- **Weak Logout Verification**: Only checks for text "Sign in", doesn't verify sign-out button gone

### Example Problem
```java
// ✗ WRONG - Shared, exposed credentials
private static final String EMAIL = "pub@prestashop.com";
private static final String PASSWORD = "123456789";

// ✓ CORRECT
private static final String EMAIL = System.getenv("QA_TEST_USER_EMAIL");
private static final String PASSWORD = System.getenv("QA_TEST_USER_PASSWORD");
```

### Major Issues
- Missing account update persistence verification
- Debug output in TC24 (System.out.println)

---

## Cross-Cutting Issues

| Issue | Severity | Count | Impact |
|-------|----------|-------|--------|
| String matching assertions | CRITICAL | 30+ | False positives/negatives |
| Hard-coded delays | HIGH | 8+ | 20+ seconds wasted per run |
| Hard-coded test data | HIGH | 15+ locations | Not maintainable |
| Debug output | LOW | 4 instances | Clutters logs |

---

## Recommendations (Priority Order)

### Priority 1: CRITICAL (Fix Immediately)
1. **Replace all `getPageSource()` assertions** with element verification via `WaitUtils`
2. **Fix TC34 incomplete test logic** - add actual huge value validation
3. **Fix meaningless assertions** (TC35, TC36) - use `assertEquals()` instead of `>=`
4. **Remove hard-coded Thread.sleep()** - use `WaitUtils.waitForVisible()` 

### Priority 2: MAJOR (Fix Within 2 Weeks)
5. **Move credentials to environment variables** - remove from source code
6. **Create TestDataFactory** - centralize email/name/birthday generation
7. **Add integer parsing validation** - handle empty/non-numeric cases
8. **Remove debug output** - clean up System.out.println statements

### Priority 3: MAJOR (Fix Within 4 Weeks)
9. **Add intermediate step verification** in checkout flow
10. **Simplify frame switching** - remove unnecessary context switches
11. **Add account persistence verification** - re-login after update
12. **Expand test coverage** - add edge cases and negative scenarios

---

## Risk Assessment

**Overall Assessment: HIGH RISK - Production Deployment NOT Recommended**

- **11 High-Risk Tests**: String matching, weak assertions, hard-coded values
- **7 Medium-Risk Tests**: Intermittent failures from timing/frame issues
- **Security: CRITICAL** - Credentials exposed in public repository

**Test Statistics:**
- Total Tests: 18
- Critical Issues: 12
- Major Issues: 18
- Minor Issues: 14
- Maintainability Score: 3/10
- Reliability Score: 4/10

---


