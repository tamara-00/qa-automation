package tests.search_filter.manual;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import utils.FrameUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManualSearch_TC05 extends BaseTest {

    private final String[] keywords = {
            "mug",
            "t-shirt",
            "cushion",
            "notebook",
            "poster"
    };

    @Test
    void shouldDisplayRelevantProductsForValidSearchKeywords() {

        for (String keyword : keywords) {

            HomePage homePage =
                    new HomePage(driver);

            driver.get(
                    "https://demo.prestashop.com/#/en/front"
            );

            FrameUtils.switchToStoreFrame(driver);

            homePage.searchProduct(keyword);

            List<String> productTitles =
                    homePage.getDisplayedSearchProductTitles();

            assertFalse(
                    productTitles.isEmpty(),
                    "Products should be displayed after searching for keyword: "
                            + keyword
            );

            boolean relatedProductFound = false;

            for (String productTitle : productTitles) {

                String normalizedTitle =
                        productTitle.toLowerCase();

                System.out.println(
                        "Keyword: "
                                + keyword
                                + " | Product: "
                                + normalizedTitle
                );

                if (normalizedTitle.contains(keyword)) {
                    relatedProductFound = true;
                }
            }

            assertTrue(
                    relatedProductFound,
                    "At least one displayed product should match the searched keyword: "
                            + keyword
            );
        }
    }
}