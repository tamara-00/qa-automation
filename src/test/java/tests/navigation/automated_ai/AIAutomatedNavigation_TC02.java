package tests.navigation.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.HomePage;
import utils.FrameUtils;
import utils.WaitUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedNavigation_TC02 extends BaseTest {

    @Test
    void shouldNavigateBackToHomepageWhenLogoIsClickedFromCategoryPage() {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.clickClothesCategory();

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                "Discover our favorites"
        );

        assertTrue(
                homePage.getPageText().contains("Clothes")
                        && homePage.getPageText().contains("Discover our favorites"),
                "User should first be redirected to the Clothes category page."
        );

        homePage.clickLogo();

        WaitUtils.waitForInvisible(
                driver,
                By.xpath("//*[contains(normalize-space(),'Discover our favorites')]")
        );

        assertFalse(
                homePage.getPageText().contains("Discover our favorites"),
                "User should leave the Clothes category page after clicking the logo."
        );
    }
}