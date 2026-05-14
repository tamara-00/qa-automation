package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.FrameUtils;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    private By signInButton = By.cssSelector("a[href*='login'], a[href*='my-account']");
    private By userProfileName = By.cssSelector(".header-block__title");
    private By firstProduct =
            By.cssSelector(
                    "article.product-miniature a"
            );

    private By addToCartButton =
            By.cssSelector(
                    "button.product__add-to-cart-button[data-button-action='add-to-cart']"
            );

    private By modalCheckoutButton =
            By.cssSelector(
                    "#blockcart-modal .btn-primary"
            );

    private By shoppingCartTitle =
            By.xpath(
                    "//h1[contains(text(),'Shopping Cart')]"
            );

    private By checkoutButton =
            By.cssSelector(
                    ".cart-summary .btn-primary"
            );

    private By mrRadio =
            By.cssSelector(
                    "input[name='id_gender'][value='1']"
            );

    private By firstNameField =
            By.name("firstname");

    private By lastNameField =
            By.name("lastname");

    private By emailField =
            By.name("email");

    private By passwordField =
            By.id("field-password");

    private By birthdayField =
            By.name("birthday");

    private By termsCheckbox =
            By.name("psgdpr");

    private By privacyCheckbox =
            By.name("customer_privacy");

    private By continueButton =
            By.cssSelector(
                    "button[type='submit']"
            );

    private By addressField =
            By.name("address1");

    private By cityField =
            By.name("city");

    private By postcodeField =
            By.name("postcode");

    private By countryDropdown =
            By.name("id_country");

    private By continueAddressButton =
            By.name("confirm-addresses");

    private By shippingStep =
            By.id("delivery");

    private By continueShippingButton =
            By.name("confirmDeliveryOption");

    private By cartBadge =
            By.cssSelector(
                    ".header-block__badge"
            );

    private By cartButton =
            By.cssSelector(
                    ".header-block__action-btn"
            );
    private By addToCartSimpleButton =
            By.cssSelector(
                    "button[data-button-action='add-to-cart']"
            );

    private By cartPageButton =
            By.cssSelector(
                    ".header-block__action-btn"
            );

    private By searchInput =
            By.cssSelector("input[placeholder='Search products...']");

    private By searchResultsTitle =
            By.cssSelector("h1.page-title-section");

    // ================= CHECKOUT METHODS =================
    public void clickSimpleAddToCart() {

        WebElement button =
                WaitUtils.waitForClickable(
                        driver,
                        addToCartSimpleButton
                );

        WaitUtils.scrollIntoView(
                driver,
                button
        );

        WaitUtils.jsClick(
                driver,
                button
        );
    }

    public boolean isCartAccessible() {

        try {

            WebElement button =
                    WaitUtils.waitForClickable(
                            driver,
                            cartPageButton
                    );

            WaitUtils.scrollIntoView(
                    driver,
                    button
            );

            WaitUtils.jsClick(
                    driver,
                    button
            );

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public int getCartCount() {

        try {

            WebElement badge =
                    WaitUtils.waitForVisible(
                            driver,
                            cartBadge
                    );

            String text =
                    badge.getText().trim();

            if (text.isEmpty()) {

                return 0;
            }

            return Integer.parseInt(text);

        } catch (Exception e) {

            return 0;
        }
    }

    public void refreshStorePage()
            throws Exception {

        driver.navigate().refresh();

        driver.switchTo().defaultContent();

        Thread.sleep(3000);

        driver.switchTo().frame(0);

        Thread.sleep(3000);
    }

    public void openInvalidProductPage() {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "window.location.hash='#/en/999999-nonexistent-product.html';"
                );
    }
    public void openFirstProduct() {

        WebElement product =
                WaitUtils.waitForClickable(
                        driver,
                        firstProduct
                );

        WaitUtils.robustClick(
                driver,
                product
        );
    }

    public void clickAddToCart() {

        WebElement button =
                WaitUtils.waitForClickable(
                        driver,
                        addToCartButton
                );

        WaitUtils.robustClick(
                driver,
                button
        );
    }

    public void proceedToCheckoutFromModal() {

        try {

            WebElement button =
                    WaitUtils.waitForClickable(
                            driver,
                            modalCheckoutButton
                    );

            WaitUtils.robustClick(
                    driver,
                    button
            );

        } catch (Exception e) {

            driver.navigate().to(
                    "https://demo.prestashop.com/#/en/cart"
            );
        }
    }

    public boolean isShoppingCartPageDisplayed() {

        try {

            return WaitUtils.waitForVisible(
                    driver,
                    shoppingCartTitle
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    public void clickCheckoutButton() {

        WebElement button =
                WaitUtils.waitForClickable(
                        driver,
                        checkoutButton
                );

        WaitUtils.scrollIntoView(
                driver,
                button
        );

        WaitUtils.robustClick(
                driver,
                button
        );
    }

    public void fillPersonalInformation(
            String firstName,
            String lastName,
            String email,
            String birthday
    ) {

        try {

            if (driver.findElements(mrRadio).size() > 0) {

                WebElement radio =
                        WaitUtils.waitForClickable(
                                driver,
                                mrRadio
                        );

                WaitUtils.jsClick(
                        driver,
                        radio
                );
            }

        } catch (Exception ignored) {
        }

        try {

            WebElement firstNameInput =
                    WaitUtils.waitForVisible(
                            driver,
                            firstNameField
                    );

            firstNameInput.clear();

            firstNameInput.sendKeys(firstName);

        } catch (Exception ignored) {
        }

        try {

            WebElement lastNameInput =
                    WaitUtils.waitForVisible(
                            driver,
                            lastNameField
                    );

            lastNameInput.clear();

            lastNameInput.sendKeys(lastName);

        } catch (Exception ignored) {
        }

        try {

            WebElement emailInput =
                    WaitUtils.waitForVisible(
                            driver,
                            emailField
                    );

            emailInput.clear();

            emailInput.sendKeys(email);

        } catch (Exception ignored) {
        }

        // IMPORTANT FIX
        try {

            if (driver.findElements(
                    By.name("password-form__check")
            ).size() > 0) {

                WebElement checkbox =
                        driver.findElement(
                                By.name("password-form__check")
                        );

                WaitUtils.jsClick(
                        driver,
                        checkbox
                );

                Thread.sleep(1000);
            }

            if (driver.findElements(passwordField).size() > 0) {

                WebElement passwordInput =
                        WaitUtils.waitForVisible(
                                driver,
                                passwordField
                        );

                WaitUtils.scrollIntoView(
                        driver,
                        passwordInput
                );

                WaitUtils.jsClick(
                        driver,
                        passwordInput
                );

                passwordInput.sendKeys(
                        "StrongPass123!"
                );
            }

        } catch (Exception ignored) {
        }

        try {

            if (driver.findElements(birthdayField).size() > 0) {

                WebElement birthdayInput =
                        WaitUtils.waitForVisible(
                                driver,
                                birthdayField
                        );

                birthdayInput.sendKeys(birthday);
            }

        } catch (Exception ignored) {
        }

        try {

            if (driver.findElements(
                    termsCheckbox
            ).size() > 0) {

                WaitUtils.jsClick(
                        driver,
                        driver.findElement(
                                termsCheckbox
                        )
                );
            }

        } catch (Exception ignored) {
        }

        try {

            if (driver.findElements(
                    privacyCheckbox
            ).size() > 0) {

                WaitUtils.jsClick(
                        driver,
                        driver.findElement(
                                privacyCheckbox
                        )
                );
            }

        } catch (Exception ignored) {
        }
    }

    public void clickContinuePersonalInformation() {

        try {

            By continueButton1 =
                    By.cssSelector(
                            "button.continue.btn.btn-primary"
                    );

            WebElement button =
                    WaitUtils.waitForClickable(
                            driver,
                            continueButton1
                    );

            WaitUtils.scrollIntoView(
                    driver,
                    button
            );

            WaitUtils.jsClick(
                    driver,
                    button
            );

            return;

        } catch (Exception ignored) {
        }

        try {

            By continueButton2 =
                    By.cssSelector(
                            "button[type='submit']"
                    );

            WebElement button =
                    WaitUtils.waitForClickable(
                            driver,
                            continueButton2
                    );

            WaitUtils.scrollIntoView(
                    driver,
                    button
            );

            WaitUtils.jsClick(
                    driver,
                    button
            );

            return;

        } catch (Exception ignored) {
        }

        try {

            By continueButton3 =
                    By.xpath(
                            "//button[contains(.,'Continue')]"
                    );

            WebElement button =
                    WaitUtils.waitForClickable(
                            driver,
                            continueButton3
                    );

            WaitUtils.scrollIntoView(
                    driver,
                    button
            );

            WaitUtils.jsClick(
                    driver,
                    button
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not click continue personal information button"
            );
        }
    }

    public boolean isAddressStepVisible() {

        return driver.findElements(
                addressField
        ).size() > 0;
    }

    public void fillAddressInformation() {

        driver.findElement(addressField)
                .sendKeys("123 Main St");

        driver.findElement(cityField)
                .sendKeys("Paris");

        driver.findElement(postcodeField)
                .sendKeys("75001");

        org.openqa.selenium.support.ui.Select country =
                new org.openqa.selenium.support.ui.Select(
                        driver.findElement(countryDropdown)
                );

        country.selectByVisibleText("France");
    }

    public void continueAddressStep() {

        WebElement button =
                WaitUtils.waitForClickable(
                        driver,
                        continueAddressButton
                );

        WaitUtils.scrollIntoView(
                driver,
                button
        );

        WaitUtils.robustClick(
                driver,
                button
        );
    }

    public boolean isShippingStepVisible() {

        try {

            return WaitUtils.waitForVisible(
                    driver,
                    shippingStep
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    public void continueShippingStep() {

        try {

            WebElement button =
                    WaitUtils.waitForClickable(
                            driver,
                            continueShippingButton
                    );

            WaitUtils.scrollIntoView(
                    driver,
                    button
            );

            WaitUtils.jsClick(
                    driver,
                    button
            );

        } catch (Exception ignored) {
        }
    }

    public String getCartBadgeValue() {

        return WaitUtils.waitForVisible(
                driver,
                cartBadge
        ).getText().trim();
    }

    public void clickCartButton() {

        WebElement button =
                WaitUtils.waitForClickable(
                        driver,
                        cartButton
                );

        WaitUtils.scrollIntoView(
                driver,
                button
        );

        WaitUtils.jsClick(
                driver,
                button
        );
    }


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void switchToStoreFrame() {
        FrameUtils.switchToStoreFrame(driver);
    }

    public void clickSignIn() {
        WaitUtils.waitForClickable(driver, signInButton).click();
    }

    public void clickUserProfile() {

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(20)
                );

        WebElement element =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                userProfileName
                        )
                );

        try {

            element.click();

        } catch (Exception e) {

            WaitUtils.jsClick(
                    driver,
                    element
            );
        }
    }

    public void switchLanguage(String language) {

        WebElement languageDropdownElement =
                WaitUtils.waitForClickable(
                        driver,
                        By.cssSelector("select.js-language-selector")
                );

        Select languageDropdown =
                new Select(languageDropdownElement);

        languageDropdown.selectByVisibleText(language);
    }
    public void clickClothesCategory() {

        WebElement clothesCategory =
                WaitUtils.waitForClickable(
                        driver,
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and normalize-space()='Clothes']")
                );

        clothesCategory.click();
    }

    public void clickLogo() {

        WebElement logo =
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector("a.navbar-brand")
                );

        WaitUtils.jsClick(driver, logo);
    }
    public String getPageText() {

        return WaitUtils.waitForVisible(
                driver,
                By.tagName("body")
        ).getText();
    }
    public void clickFooterLink(String footerLinkSelector) {

        WebElement footerLink =
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector(footerLinkSelector)
                );

        WaitUtils.scrollIntoView(driver, footerLink);

        WaitUtils.jsClick(driver, footerLink);
    }

    public String getCurrentFrameUrl() {

        return (String) ((JavascriptExecutor) driver)
                .executeScript("return window.location.href;");
    }

    public void hoverCategory(String categoryName) {

        WebElement category =
                WaitUtils.waitForVisible(
                        driver,
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and normalize-space()='" + categoryName + "']")
                );

        new Actions(driver)
                .moveToElement(category)
                .perform();
    }


    public boolean isSubcategoryVisible(String subcategoryName) {

        return !driver.findElements(
                By.xpath("//a[contains(normalize-space(),'" + subcategoryName + "')]")
        ).isEmpty();
    }

    public void clickCategory(String categoryName) {

        WebElement category =
                WaitUtils.waitForClickable(
                        driver,
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and normalize-space()='" + categoryName + "']")
                );

        category.click();
    }
    public void searchForProduct(String keyword) {

        WebElement searchBar =
                WaitUtils.waitForClickable(
                        driver,
                        searchInput
                );

        searchBar.click();

        searchBar.clear();

        searchBar.sendKeys(keyword);

        searchBar.sendKeys(Keys.ENTER);
    }

    public String getSearchResultsTitle() {

        return WaitUtils.waitForVisible(
                driver,
                searchResultsTitle
        ).getText();
    }
    public void openPriceFilter() {
        WebElement priceFilterSection =
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector("section[data-type='price']")
                );

        WaitUtils.scrollIntoView(driver, priceFilterSection);

        WebElement priceFilterButton =
                WaitUtils.waitForClickable(
                        driver,
                        By.cssSelector("section[data-type='price'] button.accordion-button")
                );

        WaitUtils.robustClick(driver, priceFilterButton);
    }

    public void moveLowerPriceSlider(int offset) {
        String previousRange = getSelectedPriceRange();

        WebElement lowerHandle =
                WaitUtils.waitForClickable(
                        driver,
                        By.cssSelector("section[data-type='price'] .noUi-handle-lower")
                );

        WaitUtils.scrollIntoView(driver, lowerHandle);

        new Actions(driver)
                .moveToElement(lowerHandle)
                .clickAndHold()
                .moveByOffset(offset, 0)
                .release()
                .perform();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver ->
                        !getSelectedPriceRange().equals(previousRange)
                );
    }

    public String getSelectedPriceRange() {
        return WaitUtils.waitForVisible(
                driver,
                By.cssSelector("section[data-type='price'] .search-filters__slider-values")
        ).getText();
    }

    public List<WebElement> getProductPrices() {

        return driver.findElements(
                        By.cssSelector(".product-miniature__price")
                )
                .stream()
                .filter(WebElement::isDisplayed)
                .toList();
    }

    public void waitUntilAllProductPricesAreWithinRange(String selectedRange) {

        double minPrice =
                extractSinglePrice(selectedRange.split("-")[0]);

        double maxPrice =
                extractSinglePrice(selectedRange.split("-")[1]);

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(driver -> {

                    List<WebElement> prices =
                            getProductPrices();

                    if (prices.isEmpty()) {
                        return false;
                    }

                    for (WebElement price : prices) {

                        if (!price.isDisplayed()) {
                            continue;
                        }

                        String priceText =
                                price.getText().trim();

                        if (priceText.isBlank()) {
                            continue;
                        }

                        double actualPrice =
                                extractSinglePrice(priceText);

                        if (actualPrice < minPrice
                                || actualPrice > maxPrice) {

                            return false;
                        }
                    }

                    return true;
                });
    }

    private double extractSinglePrice(String priceText) {
        String cleanedPrice = priceText
                .replace("€", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "")
                .trim();

        return Double.parseDouble(cleanedPrice);
    }


    public void openNewProductsPage() {

        WebElement allNewProductsButton =
                WaitUtils.waitForClickable(
                        driver,
                        By.cssSelector("a[href*='new-products']")
                );

        WaitUtils.scrollIntoView(
                driver,
                allNewProductsButton
        );

        WaitUtils.jsClick(
                driver,
                allNewProductsButton
        );

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                "New products"
        );
    }

    public void openCompositionFilter() {

        WebElement compositionSection =
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector("section[data-name='Composition']")
                );

        WaitUtils.scrollIntoView(
                driver,
                compositionSection
        );

        WebElement compositionButton =
                WaitUtils.waitForClickable(
                        driver,
                        By.cssSelector(
                                "section[data-name='Composition'] button.accordion-button"
                        )
                );

        if (compositionButton
                .getAttribute("class")
                .contains("collapsed")) {

            WaitUtils.jsClick(
                    driver,
                    compositionButton
            );
        }
    }

    public void selectCompositionFilter(String compositionName) {

        WebElement compositionLabel =
                WaitUtils.waitForClickable(
                        driver,
                        By.xpath(
                                "//section[@data-name='Composition']//label[contains(normalize-space(.), '"
                                        + compositionName
                                        + "')]"
                        )
                );

        WaitUtils.scrollIntoView(
                driver,
                compositionLabel
        );

        WaitUtils.jsClick(
                driver,
                compositionLabel
        );

        WaitUtils.waitForVisible(
                driver,
                By.cssSelector(
                        ".search-filters__item.facet-label.active"
                )
        );
    }

    public List<WebElement> getDisplayedProductTitles() {

        return driver.findElements(
                        By.cssSelector(".product-miniature__title")
                )
                .stream()
                .filter(WebElement::isDisplayed)
                .toList();
    }




}

