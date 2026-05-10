package tests.navigation.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedNavigationCroatian_TC01 {

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
    }

    private void switchToCroatianLanguage() {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        WebElement languageDropdownElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("select.js-language-selector")
                )
        );

        Select languageDropdown = new Select(languageDropdownElement);
        languageDropdown.selectByVisibleText("Hrvatski");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"),
                "Prijavite se"
        ));
    }

    @Test
    void shouldTranslateProductCardTextsToCroatian() {

        switchToCroatianLanguage();

        WebElement productTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("a.product-miniature__title")
                )
        );

        WebElement productPrice = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.product-miniature__prices")
                )
        );

        String actualProductTitle = productTitle.getText();

        System.out.println("Actual product title: " + actualProductTitle);

        assertTrue(
                productTitle.isDisplayed(),
                "Product title should be visible."
        );

        assertFalse(
                actualProductTitle.contains("Hummingbird")
                        || actualProductTitle.contains("printed")
                        || actualProductTitle.contains("Framed poster"),
                "Product title is still displayed in English instead of Croatian. Actual title: " + actualProductTitle
        );

        assertTrue(
                productPrice.isDisplayed(),
                "Product price should be visible."
        );
    }

    @Test
    void shouldTranslateHeaderTextsToCroatian() {

        switchToCroatianLanguage();

        WebElement loginText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(text(),'Prijavite se')]")
                )
        );

        WebElement cartText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(text(),'Košarica')]")
                )
        );

        assertTrue(loginText.isDisplayed(), "Login text should be translated to Croatian.");
        assertTrue(cartText.isDisplayed(), "Cart text should be translated to Croatian.");
    }

    @Test
    void shouldTranslateNavigationCategoriesToCroatian() {

        switchToCroatianLanguage();

        WebElement accessoriesMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and contains(text(),'Pribor')]")
                )
        );

        WebElement artMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and contains(text(),'Art')]")
                )
        );

        WebElement clothesMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and contains(text(),'Clothes')]")
                )
        );

        assertTrue(accessoriesMenu.isDisplayed(), "Accessories category should be translated to Croatian as 'Pribor'.");
        assertTrue(artMenu.isDisplayed(), "Art category should be visible in Croatian navigation.");
        assertFalse(clothesMenu.isDisplayed(), "Clothes category should not remain in English.");
    }


    @AfterEach
    void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }
}
