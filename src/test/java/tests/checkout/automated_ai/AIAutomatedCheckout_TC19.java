package tests.checkout.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedCheckout_TC19 {

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
    public void checkoutDeliveryInformation_validData() throws InterruptedException {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("article.product-miniature a")
                )
        );

        robustClick(firstProduct);

        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "button.product__add-to-cart-button[data-button-action='add-to-cart']"
                        )
                )
        );

        robustClick(addToCartButton);

        WebElement modalCheckoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("#blockcart-modal .btn-primary")
                )
        );

        robustClick(modalCheckoutButton);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[contains(text(),'Shopping Cart')]")
                )
        );


        WebElement checkoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".cart-summary .btn-primary")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        checkoutButton
                );

        robustClick(checkoutButton);


        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("firstname")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0,500)");

        WebElement mrRadio = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[name='id_gender'][value='1']")
                )
        );

        robustClick(mrRadio);


        driver.findElement(By.name("firstname"))
                .sendKeys("John");

        driver.findElement(By.name("lastname"))
                .sendKeys("Doe");

        driver.findElement(By.name("email"))
                .sendKeys(
                        "john" + System.currentTimeMillis() + "@mail.com"
                );

        WebElement createAccountCheckbox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("password-form__check")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        createAccountCheckbox
                );

        Thread.sleep(1000);

        WebElement passwordField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("password")
                )
        );

        passwordField.click();

        passwordField.sendKeys("StrongPass123!");

        WebElement birthdayField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("birthday")
                )
        );

        birthdayField.sendKeys("01/01/1999");

        WebElement termsCheckbox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("psgdpr")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        termsCheckbox
                );

        WebElement privacyCheckbox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("customer_privacy")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        privacyCheckbox
                );

        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[type='submit']")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        continueButton
                );

        robustClick(continueButton);

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

        WebElement continueAddressButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("confirm-addresses")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        continueAddressButton
                );

        robustClick(continueAddressButton);

        WebElement shippingStep = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("delivery")
                )
        );

        assertTrue(
                shippingStep.isDisplayed(),
                "User should proceed successfully to Shipping step"
        );
    }

    private void robustClick(WebElement element) {

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(element)
            );

            element.click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            element
                    );
        }
    }
}