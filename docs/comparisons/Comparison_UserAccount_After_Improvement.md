# Comparison Report: Human-Written vs. AI-Assisted Test Automation

**Author:** Tamara Stojanoska  
**Date:** May 2026  
**Environment:** Mac OS X (aarch64), Selenium 4.43.0, PrestaShop Demo

---

## 1. Feature Comparison Table

| Feature | Automated AI (TC24, 25, 26, 27) | Manual/Refactored (TC28) |
| :--- | :--- | :--- |
| **Logic Flow** | Isolated and procedural. Each test is a standalone script. | Modular and hierarchical. Focuses on reusable patterns. |
| **Stability** | **High.** Uses standardized `FrameUtils.switchToStoreFrame()` with explicit frame waits. | **High.** Additional explicit waits (`waitForPresence`) for extra robustness. |
| **Maintainability** | **High.** Uses `static final` constants for credentials and test data. | **High.** Uses `static final` constants and centralized helper methods. |
| **Code Density** | High volume of code (excellent for complex validation logic like TC24). | Lean and clean (focused on readability and "DRY" principles). |
| **Resilience** | Robust. Uses centralized `FrameUtils` for consistent iframe handling. Supports `robustClick()` and `jsClick()` fallbacks. | Robust. Uses JS injection, explicit context resetting, and layered wait strategies. |

### Detailed Metric: Accuracy vs. Structure

| Metric | AI-Assisted (Automated) | Human-Written (Refactored) |
| :--- | :--- | :--- |
| **Logic Accuracy** | High for data-driven tasks (Password complexity). | High for state-driven tasks (Session/Iframe management). |
| **Locator Accuracy** | Excellent (fast identification). | Good (but slower manual verification needed). |
| **Code Structure** | Procedural (clean, each test is standalone). Uses reusable utilities. | Modular (Logic is abstracted into helper methods). |
| **Scalability** | Good (Foundation utilities in `FrameUtils` and `WaitUtils` support growth). | Excellent (Foundation for a large automation suite). |
| **Credential Management** | Excellent (Uses `static final` constants). | Excellent (Uses `static final` constants). |

---

## 2. Qualitative Analysis

### Efficiency & Speed
* **AI-Assisted:** Excellent for generating boilerplate code and complex validation checks (e.g., the real-time password strength logic in **TC24**). It saves significant time in writing locators.
* **Human-Written:** Initially slower because the human focuses on building a foundation. However, once the foundation (like `loginUser`) is built, writing subsequent tests becomes significantly faster.

### Stability (The "M1/M2 Performance" Challenge)
* **AI-Assisted:** Uses centralized `FrameUtils.switchToStoreFrame()` which properly resets to default content, explicitly waits for frame availability, and prevents TimeoutExceptions gracefully.
* **Human-Written:** Uses additional implicit waits and state verification for maximum robustness. Layer-based approach with `WaitUtils.waitForPresence()` provides extra stability.

### Maintainability
* **AI-Assisted:** All tests now properly use `static final` constants for credentials. Implementation shows: `private static final String EMAIL = "pub@prestashop.com";`
* **Human-Written:** Using **Refactored Pattern** (TC28) means updates happen in one place. Follows the same constant pattern: `private static final String TEST_EMAIL = "pub@prestashop.com";`

**Status:** Both approaches now follow identical patterns for credential management.

---

## 3. Time Estimation (Per Test Case)

| Phase | Human-Written (Manual) | AI-Assisted |
| :--- | :--- | :--- |
| **Initial Scripting** | 45 minutes | 10 minutes |
| **Debugging/Stabilization** | 15 minutes | 25 minutes |
| **Refactoring for Reuse** | 20 minutes | 15 minutes |
| **Total Effort** | **~80 minutes** | **~50 minutes** |

**Conclusion:** AI is faster overall, with reduced debugging time due to centralized `FrameUtils` utilities. Both approaches now achieve high stability through shared framework components.

---

## 4. Final Verdict: Hybrid Approach

To achieve the best results in QA Automation:
1.  **Use AI for:** Locators, complex logic blocks (like TC24), and generating test data.
2.  **Use Shared Framework for:**
    - `FrameUtils.switchToStoreFrame()` - Standardized frame context management
    - `WaitUtils` utilities - Rich library of wait conditions and interaction helpers
    - `LoginPage` - Centralized credential handling
    - `AccountPage` - Reusable account operations
3.  **Use Human for:** Test architecture, complex test orchestration, framework enhancements, and state management strategies.

**Result:** Both AI-assisted and manual tests now share the same solid foundation, reducing maintenance overhead while preserving the strengths of each approach.

---