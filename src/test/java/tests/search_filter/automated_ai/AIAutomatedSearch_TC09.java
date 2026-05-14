package tests.search_filter.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import utils.FrameUtils;
import utils.WaitUtils;

import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedSearch_TC09 extends BaseTest {

    @Test
    void shouldDetectIncorrectProductVariantForWhiteColorFilter() {

        HomePage homePage =
                new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.clickClothesCategory();

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                "Discover our favorites"
        );

        homePage.openColorFilter();

        homePage.applyColorFilter("White");

        assertTrue(
                homePage.isColorFilterActive(),
                "White color filter should be marked as active."
        );

        String imageSrc =
                homePage.getFirstProductImageSrc();

        System.out.println(
                "White image src: " + imageSrc
        );

        assertTrue(
                imageSrc != null && imageSrc.contains("/2-default/"),
                "BUG: White filter is active, but displayed product image is not the white variant. Actual image src: "
                        + imageSrc
        );
    }

    @Test
    void shouldApplyBlackColorFilterAndDisplayProducts() {

        HomePage homePage =
                new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.clickClothesCategory();

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                "Discover our favorites"
        );

        homePage.openColorFilter();

        homePage.applyColorFilter("Black");

        assertTrue(
                homePage.isColorFilterActive(),
                "Black color filter should be marked as active."
        );

        assertTrue(
                homePage.isProductImageDisplayed(),
                "Products should be displayed after applying Black color filter."
        );
    }
}