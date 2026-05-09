package tests.product_details.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedCheckout_TC23 {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String URL =
            "https://demo.prestashop.com/#/en/front";

    @BeforeEach
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();

        driver.get(URL);
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void orderConfirmationAfterPurchase() throws Exception {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        robustClick(
                By.cssSelector("article.product-miniature a")
        );

        robustClick(
                By.cssSelector(
                        "button.product__add-to-cart-button[data-button-action='add-to-cart']"
                )
        );

        robustClick(
                By.cssSelector("#blockcart-modal .btn-primary")
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[contains(text(),'Shopping Cart')]")
                )
        );

        robustClick(
                By.cssSelector(".cart-summary .btn-primary")
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("firstname")
                )
        );

        robustClick(
                By.cssSelector(
                        "input[name='id_gender'][value='1']"
                )
        );

        driver.findElement(By.name("firstname"))
                .sendKeys("John");

        driver.findElement(By.name("lastname"))
                .sendKeys("Doe");

        driver.findElement(By.name("email"))
                .sendKeys(
                        "john"
                                + System.currentTimeMillis()
                                + "@test.com"
                );

        robustClick(
                By.name("password-form__check")
        );

        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("password")
                )
        );

        passwordField.sendKeys("StrongPass123!");

        driver.findElement(By.name("birthday"))
                .sendKeys("01/01/1999");

        robustClick(
                By.name("psgdpr")
        );

        robustClick(
                By.name("customer_privacy")
        );

        robustClick(
                By.cssSelector("button[type='submit']")
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("address1")
                )
        );

        driver.findElement(By.name("address1"))
                .sendKeys("123 Main St");

        driver.findElement(By.name("city"))
                .sendKeys("Paris");

        driver.findElement(By.name("postcode"))
                .sendKeys("75001");

        Select country = new Select(
                driver.findElement(By.name("id_country"))
        );

        country.selectByVisibleText("France");

        robustClick(
                By.name("confirm-addresses")
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("delivery")
                )
        );

        WebElement continueShippingButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.name("confirmDeliveryOption")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        continueShippingButton
                );

        Thread.sleep(1500);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        continueShippingButton
                );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[contains(text(),'Payment')]")
                )
        );

        WebElement bankWireLabel = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "label[for='payment-option-1']"
                        )
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        bankWireLabel
                );

        Thread.sleep(1000);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        bankWireLabel
                );

        WebElement termsCheckbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("conditions_to_approve[terms-and-conditions]")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        termsCheckbox
                );

        Thread.sleep(2000);

        WebElement placeOrderButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "#payment-confirmation button"
                        )
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        placeOrderButton
                );

        Thread.sleep(1000);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        placeOrderButton
                );

        Thread.sleep(5000);

        WebElement confirmationMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//*[contains(text(),'Your order is confirmed')]"
                        )
                )
        );

        assertTrue(
                confirmationMessage.isDisplayed(),
                "Order confirmation page should be displayed"
        );

        boolean paymentInfoVisible =
                driver.getPageSource()
                        .contains("Payment information");

        assertTrue(
                paymentInfoVisible,
                "Payment information should be displayed"
        );

        boolean orderDetailsVisible =
                driver.getPageSource()
                        .contains("Order details");

        assertTrue(
                orderDetailsVisible,
                "Order details should be displayed"
        );
    }

    private void robustClick(By locator) {

        try {

            WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            locator
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            element
                    );

            Thread.sleep(500);

            element.click();

        } catch (Exception e) {

            try {

                WebElement element = wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                locator
                        )
                );

                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                element
                        );

            } catch (Exception ignored) {
            }
        }
    }
}