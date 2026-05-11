package tests.error_handling.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedErrorHandling_TC35 {

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
    public void sessionTimeoutHandling_TC35() throws InterruptedException {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        WebElement addToCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-button-action='add-to-cart']")
                )
        );

        click(addToCart);

        handleModal();

        int cartBeforeRefresh = getCartCount();

        assertTrue(cartBeforeRefresh > 0,
                "Product was not added to cart");

        Thread.sleep(3000);


        driver.navigate().refresh();

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));


        int cartAfterRefresh = getCartCount();

        assertEquals(
                cartBeforeRefresh,
                cartAfterRefresh,
                "Cart items were not preserved after refresh/session simulation"
        );
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
}