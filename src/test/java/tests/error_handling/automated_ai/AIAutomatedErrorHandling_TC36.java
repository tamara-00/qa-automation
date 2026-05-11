package tests.error_handling.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedErrorHandling_TC36 {

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
    public void checkoutInterruptionSimulation_TC36()
            throws InterruptedException {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        WebElement addToCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-button-action='add-to-cart']")
                )
        );

        click(addToCart);

        handleModal();

        int cartBefore = getCartCount();

        assertTrue(cartBefore > 0,
                "Product was not added before interruption simulation");

        openCart();



        driver.navigate().refresh();

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        int cartAfter = getCartCount();

        assertEquals(
                cartBefore,
                cartAfter,
                "Cart data was lost after simulated interruption"
        );

        boolean cartStillAccessible =
                !driver.findElements(
                        By.cssSelector(".header-block__action-btn")
                ).isEmpty();

        assertTrue(cartStillAccessible,
                "User could not continue after interruption");
    }


    private void click(WebElement el) {

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

    private void handleModal() {

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

            click(closeBtn);

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

            return Integer.parseInt(
                    badge.getText().trim()
            );

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

        click(cartIcon);

        WebElement viewCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href*='cart?action=show']")
                )
        );

        click(viewCart);
    }
}