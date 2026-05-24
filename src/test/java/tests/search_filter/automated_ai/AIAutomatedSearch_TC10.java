package tests.search_filter.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import utils.FrameUtils;
import utils.WaitUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedSearch_TC10 extends BaseTest {

    @Test
    void shouldFilterProductsByDimensionOnArtPage() {

        HomePage homePage =
                new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.clickArtCategory();

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                "Art"
        );

        homePage.openDimensionFilter();

        homePage.selectDimensionFilter();

        List<WebElement> products =
                homePage.getDisplayedProducts();

        assertFalse(
                products.isEmpty(),
                "Products should be displayed after applying the 40x60cm dimension filter."
        );

        assertTrue(
                products.get(0).isDisplayed(),
                "Filtered product should be visible."
        );
    }
}