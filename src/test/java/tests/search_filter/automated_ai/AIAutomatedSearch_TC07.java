package tests.search_filter.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import utils.FrameUtils;
import utils.WaitUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedSearch_TC07 extends BaseTest {

    @Test
    void shouldFilterProductsByMultiplePriceRangesOnClothesPage() {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.clickClothesCategory();

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                "Discover our favorites"
        );

        homePage.openPriceFilter();

        String initialRange =
                homePage.getSelectedPriceRange();

        homePage.moveLowerPriceSlider(40);

        String firstRange =
                homePage.getSelectedPriceRange();

        assertNotEquals(
                initialRange,
                firstRange,
                "Price range should change after first slider movement."
        );

        homePage.waitUntilAllProductPricesAreWithinRange(firstRange);

        verifyProductPricesAreWithinRange(
                firstRange,
                homePage.getProductPrices()
        );

        homePage.moveLowerPriceSlider(40);

        String secondRange =
                homePage.getSelectedPriceRange();

        assertNotEquals(
                firstRange,
                secondRange,
                "Price range should change after second slider movement."
        );

        homePage.waitUntilAllProductPricesAreWithinRange(secondRange);

        verifyProductPricesAreWithinRange(
                secondRange,
                homePage.getProductPrices()
        );
    }

    private void verifyProductPricesAreWithinRange(
            String selectedRange,
            List<WebElement> productPrices
    ) {

        double minPrice =
                extractSinglePrice(
                        selectedRange.split("-")[0]
                );

        double maxPrice =
                extractSinglePrice(
                        selectedRange.split("-")[1]
                );

        assertFalse(
                productPrices.isEmpty(),
                "Filtered products should still be displayed after applying the price filter."
        );

        for (WebElement productPrice : productPrices) {

            if (!productPrice.isDisplayed()) {
                continue;
            }

            String priceText =
                    productPrice.getText().trim();

            if (priceText.isBlank()) {
                continue;
            }

            double actualPrice =
                    extractSinglePrice(priceText);

            assertTrue(
                    actualPrice >= minPrice && actualPrice <= maxPrice,
                    "Visible product price should be within selected range. Range: "
                            + selectedRange
                            + ", actual price: "
                            + priceText
            );
        }
    }

    private double extractSinglePrice(String priceText) {

        String cleanedPrice =
                priceText
                        .replace("€", "")
                        .replace(",", ".")
                        .replaceAll("[^0-9.]", "")
                        .trim();

        return Double.parseDouble(cleanedPrice);
    }
}


















//package tests.search_filter.automated_ai;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.jupiter.api.*;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//
//import org.openqa.selenium.interactions.Actions;
//
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AIAutomatedSearch_TC07 {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    private static final String URL =
//            "https://demo.prestashop.com/#/en/front";
//
//    @BeforeEach
//    void setUp() {
//        WebDriverManager.chromedriver().setup();
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//
//        driver = new ChromeDriver(options);
//        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
//
//        driver.manage().window().maximize();
//        driver.get(URL);
//    }
//
//    @Test
//    void shouldFilterProductsByMultiplePriceRangesOnClothesPage() {
//        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
//
//        wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and normalize-space()='Clothes']")
//        )).click();
//
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(
//                By.tagName("body"),
//                "Discover our favorites"
//        ));
//
//        WebElement priceFilterSection = wait.until(
//                ExpectedConditions.presenceOfElementLocated(
//                        By.cssSelector("section[data-type='price']")
//                )
//        );
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView({block:'center'});",
//                priceFilterSection
//        );
//
//        sleep(1500);
//
//        WebElement priceFilterButton = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.cssSelector("section[data-type='price'] button.accordion-button")
//                )
//        );
//
//        priceFilterButton.click();
//        sleep(1200);
//
//        String initialRange = getSelectedPriceRange();
//        System.out.println("Initial range: " + initialRange);
//
//        moveLowerPriceSlider(40);
//        sleep(3000);
//
//        String firstRange = getSelectedPriceRange();
//        System.out.println("First range: " + firstRange);
//
//        assertNotEquals(
//                initialRange,
//                firstRange,
//                "Price range should change after first slider movement."
//        );
//
//        verifyProductPricesAreWithinRange(firstRange);
//
//        moveLowerPriceSlider(40);
//        sleep(3000);
//
//        String secondRange = getSelectedPriceRange();
//        System.out.println("Second range: " + secondRange);
//
//        assertNotEquals(
//                firstRange,
//                secondRange,
//                "Price range should change after second slider movement."
//        );
//
//        verifyProductPricesAreWithinRange(secondRange);
//
//        sleep(3000);
//    }
//
//    private void moveLowerPriceSlider(int offset) {
//        WebElement lowerHandle = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.cssSelector("section[data-type='price'] .noUi-handle-lower")
//                )
//        );
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView({block:'center'});",
//                lowerHandle
//        );
//
//        sleep(800);
//
//        new Actions(driver)
//                .moveToElement(lowerHandle)
//                .clickAndHold()
//                .pause(Duration.ofMillis(500))
//                .moveByOffset(offset, 0)
//                .pause(Duration.ofMillis(500))
//                .release()
//                .perform();
//    }
//
//    private String getSelectedPriceRange() {
//        return wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.cssSelector("section[data-type='price'] .search-filters__slider-values")
//                )
//        ).getText();
//    }
//
//    private void verifyProductPricesAreWithinRange(String selectedRange) {
//        double minPrice = extractMinPrice(selectedRange);
//        double maxPrice = extractMaxPrice(selectedRange);
//
//        List<WebElement> productPrices = wait.until(
//                ExpectedConditions.visibilityOfAllElementsLocatedBy(
//                        By.cssSelector(".product-miniature__price")
//                )
//        );
//
//        assertFalse(
//                productPrices.isEmpty(),
//                "Filtered products should still be displayed after applying the price filter."
//        );
//
//        for (WebElement productPrice : productPrices) {
//            String priceText = productPrice.getText();
//
//            if (priceText == null || priceText.isBlank()) {
//                continue;
//            }
//
//            double actualPrice = extractSinglePrice(priceText);
//
//            assertTrue(
//                    actualPrice >= minPrice && actualPrice <= maxPrice,
//                    "Product price should be within selected range. Range: "
//                            + selectedRange
//                            + ", actual price: "
//                            + priceText
//            );
//        }
//    }
//
//    private double extractMinPrice(String rangeText) {
//        String[] parts = rangeText.split("-");
//        return extractSinglePrice(parts[0]);
//    }
//
//    private double extractMaxPrice(String rangeText) {
//        String[] parts = rangeText.split("-");
//        return extractSinglePrice(parts[1]);
//    }
//
//    private double extractSinglePrice(String priceText) {
//        String cleanedPrice = priceText
//                .replace("€", "")
//                .replace(",", ".")
//                .replaceAll("[^0-9.]", "")
//                .trim();
//
//        return Double.parseDouble(cleanedPrice);
//    }
//
//    private void sleep(int milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}