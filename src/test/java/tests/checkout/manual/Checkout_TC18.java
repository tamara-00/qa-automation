package tests.checkout.manual;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

//manual
// ~25 mins
// problems with logic to check cart page

public class Checkout_TC18 {

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
    public void cartShouldNotOpenWhenEmpty() {

        driver.get(HOME_URL);

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".header-block__badge")
                )
        );

        String badgeValue = cartBadge.getText().trim();

        assertEquals("0", badgeValue,
                "Cart should be empty");

        WebElement cartButton = driver.findElement(
                By.cssSelector(".header-block__action-btn")
        );
        cartButton.click();
        boolean cartPageOpened = driver.getPageSource()
                .contains("Shopping Cart");

        assertFalse(cartPageOpened,
                "Cart page should NOT open when cart is empty");
    }
}