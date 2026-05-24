package tests.product_details.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import pages.ProductPage;
import utils.WaitUtils;
import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedProductDetails_TC29 extends BaseTest {

    // TC.29 - Product Detail Page - Complete Information Display
    @Test
    public void TC29_verifyFullProductInformation() {
        HomePage home = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);

        home.switchToStoreFrame();

        WebElement featuredTitle = WaitUtils.waitForPresence(driver, By.cssSelector("h2.section-title"));
        WaitUtils.scrollIntoView(driver, featuredTitle);

        WebElement productImg = WaitUtils.waitForClickable(driver, By.cssSelector("img[alt='Hummingbird printed t-shirt']"));
        WaitUtils.robustClick(driver, productImg);

        driver.switchTo().defaultContent();
        WaitUtils.waitForPresence(driver, By.id("framelive"));
        home.switchToStoreFrame();

        assertEquals("Hummingbird printed t-shirt", productPage.getProductName(), "Product name mismatch");
        assertTrue(productPage.isMainImageDisplayed(), "Main image missing");

        String description = productPage.getDescriptionText();
        assertTrue(description.contains("pima cotton"), "Description text mismatch");

        String price = productPage.getPriceText();
        assertTrue(price.contains("22.94"), "Price mismatch");

        assertEquals("Color", productPage.getColorLabel(), "Variant label missing");
        assertEquals("1", productPage.getProductPageQuantityValue(), "Initial quantity should be 1");

        assertTrue(productPage.isSizeDropdownVisible(), "Size dropdown missing");
        assertTrue(productPage.isAddToCartIconVisible(), "Add to Cart button missing");
    }
}