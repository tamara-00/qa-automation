package tests.navigation.automated_ai;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedNavigation_TC02 {

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

        wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        driver.manage().window().maximize();

        driver.get(URL);
    }

    @Test
    void shouldNavigateBackToHomepageWhenLogoIsClickedFromCategoryPage() {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        WebElement clothesCategory = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class,'ps-mainmenu__tree-link') and normalize-space()='Clothes']")
                )
        );

        clothesCategory.click();

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("body"),
                        "Discover our favorites"
                )
        );

        String categoryPageText =
                driver.findElement(By.tagName("body")).getText();

        assertTrue(
                categoryPageText.contains("Clothes")
                        && categoryPageText.contains("Discover our favorites"),
                "User should first be redirected to the Clothes category page."
        );

        WebElement logo = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("a.navbar-brand")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                logo
        );

        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElementLocated(
                                By.tagName("body"),
                                "Discover our favorites"
                        )
                )
        );

        String homepageText =
                driver.findElement(By.tagName("body")).getText();

        assertFalse(
                homepageText.contains("Discover our favorites"),
                "User should leave the Clothes category page after clicking the logo."
        );
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }
}