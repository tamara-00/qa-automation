package tests.error_handling.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

// can't test 0 and letter input values due to limitations

public class AIAutomatedErrorHandling_TC34 {

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
    public void quantityShouldShowValidationErrorForHugeValues() {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        WebElement addToCart = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button[data-button-action='add-to-cart']")
                )
        );

        scrollAndClick(addToCart);

        handleModalIfPresent();

        openCart();

        WebElement quantityInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input.js-cart-line-product-quantity")
                )
        );

        quantityInput.sendKeys(Keys.CONTROL + "a");
        quantityInput.sendKeys("9999999999");

        WebElement confirmButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".js-increment-button")
                )
        );

        confirmButton.click();

        WebElement toastBody = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".toast-body")
                )
        );

        String toastText =
                toastBody.getText().toLowerCase();

        assertTrue(
                toastText.contains("300")
                        || toastText.contains("adjust")
                        || toastText.contains("quantity"),
                "Validation toast message was NOT displayed"
        );
    }



    private void scrollAndClick(WebElement el) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        el
                );

        try {

            wait.until(ExpectedConditions.elementToBeClickable(el))
                    .click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", el);
        }
    }

    private void handleModalIfPresent() {

        try {

            WebElement modal = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("#blockcart-modal.show")
                    )
            );

            WebElement closeBtn = modal.findElement(
                    By.cssSelector("button[data-bs-dismiss='modal']")
            );

            scrollAndClick(closeBtn);

        } catch (TimeoutException ignored) {

            System.out.println("No modal appeared");
        }
    }

    private void openCart() {

        WebElement cartIcon = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".header-block__action-btn")
                )
        );

        scrollAndClick(cartIcon);

        WebElement viewCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href*='cart?action=show']")
                )
        );

        scrollAndClick(viewCart);
    }
}