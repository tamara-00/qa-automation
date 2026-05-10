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

public class AIAutomatedNavigationFrench_TC01 {

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



    private void switchToFrenchLanguage() {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        WebElement languageDropdownElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("select.js-language-selector")
                )
        );

        Select languageDropdown = new Select(languageDropdownElement);

        languageDropdown.selectByVisibleText("Français");

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("body"),
                        "Connexion"
                )
        );
    }

    @Test
    void shouldTranslateHeaderTextsToFrench() {

        switchToFrenchLanguage();

        WebElement loginText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(text(),'Connexion')]")
                )
        );

        WebElement cartText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(text(),'Panier')]")
                )
        );

        assertTrue(
                loginText.isDisplayed(),
                "Login text should be translated to French."
        );

        assertTrue(
                cartText.isDisplayed(),
                "Cart text should be translated to French."
        );
    }

    @Test
    void shouldTranslateNavigationCategoriesToFrench() {

        switchToFrenchLanguage();

        WebElement clothesMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and contains(text(),'Vêtements')]")
                )
        );

        WebElement accessoriesMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and contains(text(),'Accessoires')]")
                )
        );

        WebElement artMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and contains(text(),'Art')]")
                )
        );

        assertTrue(
                clothesMenu.isDisplayed(),
                "Clothes category should be translated to French."
        );

        assertTrue(
                accessoriesMenu.isDisplayed(),
                "Accessories category should be translated to French."
        );

        assertTrue(
                artMenu.isDisplayed(),
                "Art category should be displayed in French navigation."
        );
    }

    @Test
    void shouldTranslateProductCardTextsToFrench() {

        switchToFrenchLanguage();

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

        WebElement addToCartButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[contains(.,'Ajouter au panier')]")
                )
        );

        String actualProductTitle = productTitle.getText();

        assertTrue(
                productTitle.isDisplayed(),
                "Product title should be visible."
        );

        assertFalse(
                actualProductTitle.contains("Hummingbird"),
                "Product title is still displayed in English instead of French. Actual title: " + actualProductTitle
        );

        assertTrue(
                productPrice.isDisplayed(),
                "Product price should be visible."
        );

        assertTrue(
                addToCartButton.isDisplayed(),
                "Add-to-cart button should be translated to French."
        );
    }



    @AfterEach
    void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }
}