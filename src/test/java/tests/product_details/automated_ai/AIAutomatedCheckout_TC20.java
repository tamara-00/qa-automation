package tests.product_details.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedCheckout_TC20 {

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
    public void checkoutWithExistingCustomerAccount() throws Exception {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        robustClick(
                By.xpath("//span[contains(text(),'Sign in')]")
        );

        robustClick(
                By.cssSelector(
                        "a[data-link-action='display-register-form']"
                )
        );

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.name("firstname")
                )
        );

        driver.findElement(By.name("firstname"))
                .sendKeys("John");

        driver.findElement(By.name("lastname"))
                .sendKeys("Doe");

        WebElement emailField = driver.findElement(
                By.id("field-email")
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].value='john"
                                + System.currentTimeMillis()
                                + "@test.com';",
                        emailField
                );

        WebElement passwordField = driver.findElement(
                By.id("field-password")
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].value='StrongPass123!';",
                        passwordField
                );

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

        Thread.sleep(3000);

        robustClick(
                By.cssSelector(".logo")
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

        WebElement shippingStep = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("delivery")
                )
        );

        assertTrue(
                shippingStep.isDisplayed(),
                "Checkout should proceed successfully to shipping step"
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