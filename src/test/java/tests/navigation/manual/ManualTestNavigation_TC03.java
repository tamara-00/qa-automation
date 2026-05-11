package tests.navigation.manual;

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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManualTestNavigation_TC03 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();

        driver.get("https://demo.prestashop.com/#/en/front");
    }

    @Test
    void shouldNavigateThroughMainCategories() {

        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(0)
        );

        WebElement clothesCategory = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Clothes')]")
                )
        );

        clothesCategory.click();

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("body"),
                        "Clothes"
                )
        );

        String clothesPageText = driver.findElement(By.tagName("body")).getText();

        assertTrue(
                clothesPageText.contains("Clothes"),
                "Clothes category page should be opened."
        );

        WebElement accessoriesCategory = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Accessories')]")
                )
        );

        accessoriesCategory.click();

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("body"),
                        "Accessories"
                )
        );

        String accessoriesPageText = driver.findElement(By.tagName("body")).getText();

        assertTrue(
                accessoriesPageText.contains("Accessories"),
                "Accessories category page should be opened."
        );

        WebElement artCategory = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Art')]")
                )
        );

        artCategory.click();

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("body"),
                        "Art"
                )
        );

        String artPageText = driver.findElement(By.tagName("body")).getText();

        assertTrue(
                artPageText.contains("Art"),
                "Art category page should be opened."
        );
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }
}