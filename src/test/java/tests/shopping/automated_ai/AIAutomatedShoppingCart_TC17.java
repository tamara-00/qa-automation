package tests.shopping.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedShoppingCart_TC17 {

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().window().maximize();
        driver.get(URL);
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) driver.quit();
    }

    @Test
    public void continueShopping_addAgain() {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        int initialCount = getCartCount();

        // ================= FIRST ADD =================

        WebElement addToCart = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(
                                "button[data-button-action='add-to-cart']"
                        )
                )
        );

        scrollAndClick(addToCart);


        WebElement firstModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#blockcart-modal.show")
                )
        );

        assertTrue(firstModal.isDisplayed());

        WebElement continueShopping = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "#blockcart-modal button[data-bs-dismiss='modal']"
                        )
                )
        );

        scrollAndClick(continueShopping);

        wait.until(
                ExpectedConditions.invisibilityOf(firstModal)
        );

        int afterFirstAdd = getCartCount();

        assertEquals(
                initialCount + 1,
                afterFirstAdd,
                "First product was not added"
        );


        WebElement addToCartAgain = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(
                                "button[data-button-action='add-to-cart']"
                        )
                )
        );

        scrollAndClick(addToCartAgain);


        WebElement secondModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#blockcart-modal.show")
                )
        );

        assertTrue(secondModal.isDisplayed());

        WebElement continueShoppingAgain = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                "#blockcart-modal button[data-bs-dismiss='modal']"
                        )
                )
        );

        scrollAndClick(continueShoppingAgain);

        wait.until(
                ExpectedConditions.invisibilityOf(secondModal)
        );


        int finalCount = getCartCount();

        assertEquals(
                initialCount + 2,
                finalCount,
                "Cart should contain 2 products"
        );
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

    private int getCartCount() {

        try {

            WebElement badge = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".header-block__badge")
                    )
            );

            return Integer.parseInt(
                    badge.getText().trim()
            );

        } catch (Exception e) {

            return 0;
        }
    }
}