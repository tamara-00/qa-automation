# Comparison Report: Human-Written vs. AI-Assisted Test Automation

**Author:** Tamara Stojanoska  
**Date:** May 2026  
**Environment:** Mac OS X (aarch64), Selenium 4.43.0, PrestaShop Demo

---

## 1. Feature Comparison Table

| Feature | Automated AI (TC29, 30, 31) | Manual/Refactored (TC32) |
| :--- | :--- | :--- |
| **Logic Flow** | Procedural with optimized frame/scroll patterns. Each test uses centralized utilities. | Streamlined and abstracted. Delegates to page object methods with built-in utilities. |
| **Stability** | **High.** Uses `FrameUtils.switchToStoreFrame()` with explicit waits and centralized context handling. | **High.** Uses `ProductPage` helper methods with built-in `FrameUtils` integration. |
| **Maintainability** | **High.** Uses `static final` constants for product selectors and test data. | **High.** Uses `ProductPage` helper methods and centralized constants. |
| **Code Density** | Moderate volume of code with cleaner DOM manipulation using shared utilities. | Lean and readable. Focuses on business logic rather than implementation details. |
| **Resilience** | Robust. Frame switches are handled through centralized `FrameUtils` with proper context reset mechanisms. | Robust. Page object abstraction delegates frame handling to `FrameUtils` internally. |

### Detailed Metric: Accuracy vs. Structure

| Metric | AI-Assisted (Automated) | Human-Written (Refactored) |
| :--- | :--- | :--- |
| **Logic Accuracy** | High for sequential workflows (navigation, submission). | High for state-driven validation (quantity changes, stock info). |
| **Locator Accuracy** | Excellent (CSS selectors well-identified and centralized). | Good (Selectors abstracted in `ProductPage` class with reusable methods). |
| **Code Structure** | Procedural with shared utilities (Selenium calls use `FrameUtils` and `WaitUtils`). | Modular (All DOM interactions wrapped in `ProductPage` methods with utility delegation). |
| **Scalability** | Good (Foundation utilities in `FrameUtils` support growth). | Excellent (New tests reuse existing page object methods and centralized utilities). |
| **Frame Context Management** | Excellent (Uses standardized `FrameUtils.switchToStoreFrame()` across all tests). | Excellent (Page objects internally manage context through `FrameUtils`). |

---

## 2. Qualitative Analysis

### Efficiency & Speed
* **AI-Assisted:** Excellent for rapid script generation of complex workflows. Quickly identifies CSS selectors and generates traversal logic when guided to use `FrameUtils` and `WaitUtils`.
* **Human-Written:** Initially slower because the human designs abstraction layers. However, this foundation in TC32 makes writing subsequent product detail tests significantly faster and more maintainable.

### Stability (The Frame Context Improvement)
* **AI-Assisted:** Now uses centralized `FrameUtils.switchToStoreFrame()` which properly resets to default content, explicitly waits for frame availability, and prevents TimeoutExceptions gracefully. This eliminates the previous hard-sleep approach.
* **Human-Written:** Human-written test relies on page object methods that internally manage context through `FrameUtils`. By encapsulating frame handling in `ProductPage.openProductDetails()` and similar methods, the manual test achieves better predictability and maintainability.

**Status:** Both approaches now follow identical patterns for frame context management.

### Maintainability
* **AI-Assisted:** All selector locators are now stored as `static final` constants. If product selectors change, updates are centralized in the test file or `ProductPage` constants.
* **Human-Written:** Using `ProductPage` helper methods means updates happen in one place. If the underlying DOM changes, only the `ProductPage` class needs a fix.

---

## 3. Time Estimation (Per Test Case)

| Phase | Human-Written (Manual) | AI-Assisted |
| :--- | :--- | :--- |
| **Initial Scripting** | 60 minutes | 15 minutes |
| **Debugging/Stabilization** | 10 minutes | 25 minutes (Iterative with framework utilities) |
| **Refactoring for Reuse** | 25 minutes | 15 minutes (Using `FrameUtils` and `WaitUtils` reduces refactoring time) |
| **Total Effort** | **~95 minutes** | **~55 minutes** |

**Conclusion:** AI is **42% faster** overall with improved stability through shared framework components. Human intervention is necessary for architectural stability and page object design.

---

## 4. Final Verdict: Hybrid Approach

To achieve the best results in QA Automation for Product Details testing:

1. **Use AI for:**
    - Generating initial locators and complex multi-step workflows (like TC31)
    - Rapidly scripting comprehensive validation logic
    - Using existing framework utilities to ensure consistency

2. **Use Shared Framework for:**
    - `FrameUtils.switchToStoreFrame()` - Standardized frame context management
    - `WaitUtils` utilities - Rich library of wait conditions and interaction helpers
    - `ProductPage` - Reusable product operations
    - Centralized selector constants via `static final`

3. **Use Human for:**
    - Designing the `ProductPage` abstraction layer (critical for maintainability)
    - Refactoring repetitive patterns into reusable methods
    - Building constants and hardcoded values into centralized locations
    - Testing on Mac Silicon to ensure timing expectations are realistic

**Result:** Both AI-assisted and manual tests now share the same solid foundation, reducing maintenance overhead while preserving the strengths of each approach.

---


