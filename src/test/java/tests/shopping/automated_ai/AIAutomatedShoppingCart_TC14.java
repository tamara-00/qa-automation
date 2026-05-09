package tests.shopping.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedShoppingCart_TC14 {

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
    public void addMultipleQuantities_sameProduct() throws Exception {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        for (int i = 0; i < 5; i++) {

            WebElement plusButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("increment_button_1")
                    )
            );

            jsClick(plusButton);

            Thread.sleep(700);
        }

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

        Thread.sleep(3000);

        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".header-block__badge")
                )
        );

        String cartCount = cartBadge.getText().trim();

        System.out.println("Cart count: " + cartCount);

        assertEquals(
                "5",
                cartCount,
                "Cart should contain 5 items"
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