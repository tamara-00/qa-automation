package tests.product_details.manual;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.ProductPage;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManualProductDetails_TC32 extends BaseTest {

    // TC.32 - Product Detail Page - Stock Status Display & Behavior of Quantity Selectors
    @Test
    public void TC32_verifyStockAndQtySelectors() {
        HomePage home = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        home.switchToStoreFrame();
        WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("img[alt='Hummingbird printed t-shirt']")
        ));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
        js.executeScript("arguments[0].click();", product);
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("framelive")));
        home.switchToStoreFrame();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#product_details_heading button")));
        productPage.openProductDetails();

        String stockInfo = wait.until(d -> {
            String text = productPage.getStockQuantity();
            return (text != null && !text.isEmpty()) ? text : null;
        });
        assertTrue(stockInfo.contains("Items"), "Stock info missing");

        assertEquals("1", productPage.getProductPageQuantityValue());
        productPage.clickIncrementOnProductPage();
        wait.until(d -> productPage.getProductPageQuantityValue().equals("2"));
        assertEquals("2", productPage.getProductPageQuantityValue());
        productPage.clickDecrementOnProductPage();
        wait.until(d -> productPage.getProductPageQuantityValue().equals("1"));
        assertEquals("1", productPage.getProductPageQuantityValue());
    }
}