package tests.navigation.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedShoppingCart_TC15 {

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
    public void removeItemFromCart() {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );


        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "button[data-button-action='add-to-cart']"
                        )
                )
        );

        jsClick(addToCartButton);


        WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("blockcart-modal")
                )
        );

        assertTrue(
                modal.isDisplayed(),
                "Cart modal should appear"
        );


        WebElement proceedToCheckout = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "#blockcart-modal a[href*='cart?action=show']"
                        )
                )
        );

        jsClick(proceedToCheckout);


        WebElement quantityInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                "input.js-cart-line-product-quantity"
                        )
                )
        );

        assertEquals(
                "1",
                quantityInput.getAttribute("value"),
                "Cart should initially contain 1 product"
        );


        WebElement minusButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("decrement_button_1")
                )
        );

        jsClick(minusButton);


        wait.until(driver -> {

            try {

                WebElement badge = driver.findElement(
                        By.cssSelector(".header-block__badge")
                );

                return badge.getText().trim().equals("0");

            } catch (Exception e) {

                return true;
            }
        });

        WebElement cartBadge = driver.findElement(
                By.cssSelector(".header-block__badge")
        );

        assertEquals(
                "0",
                cartBadge.getText().trim(),
                "Cart should be empty after removing item"
        );
    }


    private void jsClick(WebElement element) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        element
                );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element
                );
    }
}