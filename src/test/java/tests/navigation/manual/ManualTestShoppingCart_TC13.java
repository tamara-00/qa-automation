package tests.navigation.manual;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManualTestShoppingCart_TC13 {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void addProductToCart_verifyModalAppears() {

        driver.get("https://demo.prestashop.com/#/en/front");

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );


        WebElement addToCart = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(
                                "button[data-button-action='add-to-cart']"
                        )
                )
        );

        scrollAndClick(addToCart);


        WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#blockcart-modal.show")
                )
        );

        assertTrue(
                modal.isDisplayed(),
                "Add to cart modal should appear"
        );
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }


    private void scrollAndClick(WebElement el) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        el
                );

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(el)
            ).click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            el
                    );
        }
    }
}