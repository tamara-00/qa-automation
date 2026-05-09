package tests.navigation.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedShoppingCart_TC16 {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String URL = "https://demo.prestashop.com/#/en/front";

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
    public void updateQuantity_endToEnd() {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        int initialCount = getCartCount();

        WebElement addToCart = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button[data-button-action='add-to-cart']")
                )
        );

        scrollAndClick(addToCart);

        handleModalIfPresent();

        int afterAdd = getCartCount();

        assertEquals(initialCount + 1, afterAdd, "Product not added");

        openCart();

        for (int i = 0; i < 2; i++) {

            wait.until(driver -> {

                try {

                    WebElement plus = driver.findElement(
                            By.cssSelector(".js-increment-button")
                    );

                    plus.click();

                    return true;

                } catch (StaleElementReferenceException e) {

                    return false;
                }
            });
        }

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input.js-cart-line-product-quantity")
                )
        );

        wait.until(d -> input.getAttribute("value").equals("3"));

        assertEquals(
                "3",
                input.getAttribute("value"),
                "Quantity not updated correctly"
        );
    }

    private void scrollAndClick(WebElement el) {

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);

        try {

            wait.until(ExpectedConditions.elementToBeClickable(el)).click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", el);
        }
    }

    private void handleModalIfPresent() {

        try {

            WebElement modal = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("#blockcart-modal.show")
                    ));

            WebElement closeBtn = modal.findElement(
                    By.cssSelector("button[data-bs-dismiss='modal']")
            );

            scrollAndClick(closeBtn);

        } catch (TimeoutException ignored) {

            System.out.println("No modal appeared");
        }
    }

    private int getCartCount() {

        try {

            WebElement badge = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".header-block__badge")
                    )
            );

            return Integer.parseInt(badge.getText().trim());

        } catch (Exception e) {

            return 0;
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