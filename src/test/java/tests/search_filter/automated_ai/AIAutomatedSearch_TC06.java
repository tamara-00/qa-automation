package tests.search_filter.automated_ai;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.HomePage;
import utils.FrameUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedSearch_TC06 extends BaseTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "xyz123abc",
            "3",
            "zzz"
    })
    void shouldShowNoResultsMessageForInvalidSearchKeywords(String invalidKeyword) {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.searchForProduct(invalidKeyword);

        assertTrue(
                homePage.getSearchResultsTitle().contains(
                        "No search results for \"" + invalidKeyword + "\""
                ),
                "No-results message should be displayed for invalid keyword: "
                        + invalidKeyword
        );
    }
}