package tests.product_details.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import pages.ProductPage;
import utils.WaitUtils;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedProductDetails_TC30 extends BaseTest {

    // TC.30 - Product Image Gallery - Zoom and Modal
    @Test
    public void TC30_verifyImageZoomAndModal() {
        HomePage home = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);

        home.switchToStoreFrame();

        WebElement productImg = WaitUtils.waitForClickable(driver, By.cssSelector("img[alt='Hummingbird printed t-shirt']"));
        WaitUtils.robustClick(driver, productImg);

        driver.switchTo().defaultContent();
        WaitUtils.waitForPresence(driver, By.id("framelive"));
        home.switchToStoreFrame();

        WebElement zoomBtn = WaitUtils.waitForPresence(driver, By.cssSelector("button.product__zoom"));
        WaitUtils.jsClick(driver, zoomBtn);

        boolean isDisplayed = productPage.isZoomModalDisplayed();

        assertTrue(isDisplayed, "Zoom modal not detected");
    }
}