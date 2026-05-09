package tests.product_details.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedCheckout_TC21 {

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
    public void checkoutWithInvalidAddress_missingRequiredFields()
            throws Exception {

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

        Thread.sleep(2000);

        boolean validationExists =
                driver.getPageSource()
                        .contains("required");

        assertTrue(
                validationExists,
                "Validation message should appear for required fields"
        );

        boolean stillOnPersonalInfo =
                driver.getPageSource()
                        .contains("Personal Information");

        assertTrue(
                stillOnPersonalInfo,
                "Form should not submit with missing required fields"
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