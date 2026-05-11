package tests.error_handling.manual;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

// ~15 mins generation + fixes
//Additional server-side error scenarios such as
// 403 Forbidden and 500 Internal Server Error could not be validated due to
// limitations of the public demo environment.

public class ErrorHandling_TC33 {

    WebDriver driver;
    WebDriverWait wait;

    String HOME_URL = "https://demo.prestashop.com/#/en/front";

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void invalidProductShouldNotOpenValidProductPage() {

        driver.get(HOME_URL);

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        ((JavascriptExecutor) driver).executeScript(
                "window.location.hash='#/en/999999-nonexistent-product.html';"
        );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String page = driver.getPageSource().toLowerCase();

        boolean validProductLoaded =
                page.contains("add to cart")
                        || page.contains("product-details")
                        || page.contains("quantity");

        assertFalse(validProductLoaded,
                "Invalid product should NOT load valid product page");
    }
}