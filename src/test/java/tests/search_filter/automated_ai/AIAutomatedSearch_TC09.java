package tests.search_filter.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedSearch_TC09 {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String URL =
            "https://demo.prestashop.com/#/en/front";

    @BeforeEach
    void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();

        driver.get(URL);

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );
    }

    @Test
    void shouldDetectIncorrectProductVariantForWhiteColorFilter() {

        openClothesCategory();

        applyColorFilter("White");

        WebElement selectedColor = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".search-filters__item.facet-label.active .color.active")
                )
        );

        assertTrue(
                selectedColor.isDisplayed(),
                "White color filter should be marked as active."
        );

        WebElement productImage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".product-miniature__image")
                )
        );

        String imageSrc = productImage.getAttribute("src");

        System.out.println("White image src: " + imageSrc);

        assertTrue(
                imageSrc != null && imageSrc.contains("/2-default/"),
                "BUG: White filter is active, but displayed product image is not the white variant. Actual image src: "
                        + imageSrc
        );
    }

    @Test
    void shouldApplyBlackColorFilterAndDisplayProducts() {

        openClothesCategory();

        applyColorFilter("Black");

        WebElement selectedColor = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".search-filters__item.facet-label.active .color.active")
                )
        );

        assertTrue(
                selectedColor.isDisplayed(),
                "Black color filter should be marked as active."
        );

        WebElement productImage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".product-miniature__image")
                )
        );

        assertTrue(
                productImage.isDisplayed(),
                "Products should be displayed after applying Black color filter."
        );
    }

    private void openClothesCategory() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and normalize-space()='Clothes']")
                )
        ).click();

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("body"),
                        "Discover our favorites"
                )
        );

        WebElement colorFilterSection = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("section[data-name='Color']")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                colorFilterSection
        );

        WebElement colorFilterButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("section[data-name='Color'] button.accordion-button")
                )
        );

        colorFilterButton.click();
    }

    private void applyColorFilter(String colorName) {

        WebElement colorLabel = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(
                                "//input[contains(@data-search-url,'Color-"
                                        + colorName
                                        + "')]/following-sibling::label"
                        )
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                colorLabel
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                colorLabel
        );

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(".search-filters__item.facet-label.active")
                )
        );
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }
}