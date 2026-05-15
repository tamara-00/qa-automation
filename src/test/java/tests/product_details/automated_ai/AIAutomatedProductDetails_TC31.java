package tests.product_details.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utils.WaitUtils;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedProductDetails_TC31 extends BaseTest {

    private static final String EMAIL = "pub@prestashop.com";
    private static final String PASSWORD = "123456789";

    // TC.31 - Product Details - Product Review & Rating Submission by Logged-in User
    @Test
    public void TC31_verifyLoggedUserCanWriteReview() {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        home.switchToStoreFrame();
        home.clickSignIn();
        login.login(EMAIL, PASSWORD);

        driver.switchTo().defaultContent();
        home.switchToStoreFrame();

        WebElement product = WaitUtils.waitForClickable(driver, By.cssSelector("img[alt='Hummingbird printed t-shirt']"));
        WaitUtils.robustClick(driver, product);

        driver.switchTo().defaultContent();
        WaitUtils.waitForPresence(driver, By.id("framelive"));
        home.switchToStoreFrame();

        WebElement reviewBtn = WaitUtils.waitForPresence(driver, By.id("product-additional-info-review-button"));
        WaitUtils.scrollIntoView(driver, reviewBtn);
        WaitUtils.jsClick(driver, reviewBtn);

        boolean isModalOpen = productPage.isReviewModalDisplayed();
        assertTrue(isModalOpen, "Review modal not displayed");

        productPage.fillReviewForm("Excellent!", "Best quality t-shirt I have ever bought.");
        productPage.submitReview();
    }
}