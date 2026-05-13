package tests.navigation.automated_ai;

import base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;

import pages.HomePage;
import utils.FrameUtils;
import utils.WaitUtils;

import static org.junit.jupiter.api.Assertions.*;

public class AIAutomatedNavigation_TC01 extends BaseTest {

    private boolean isTextVisible(String text) {

        return !driver.findElements(
                By.xpath("//*[contains(normalize-space(),'" + text + "')]")
        ).isEmpty();
    }

    private boolean isMenuTextVisible(String text) {

        return !driver.findElements(
                By.xpath(
                        "//a[contains(@class,'ps-mainmenu__tree-link') and contains(normalize-space(),'" + text + "')]"
                )
        ).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "Hrvatski, Prijavite se, Košarica, Pribor",
            "Français, Connexion, Panier, Accessoires"
    })
    void shouldDisplayTranslatedNavigationAndHeaderElementsAfterLanguageSwitch(
            String language,
            String loginText,
            String cartText,
            String accessoriesText
    ) {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.switchLanguage(language);

        FrameUtils.switchToDefaultContent(driver);
        FrameUtils.switchToStoreFrame(driver);

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                loginText
        );

        assertTrue(
                isTextVisible(loginText),
                "Login text should be translated correctly for language: "
                        + language
        );

        assertTrue(
                isTextVisible(cartText),
                "Cart text should be translated correctly for language: "
                        + language
        );

        assertTrue(
                isMenuTextVisible(accessoriesText),
                "Accessories category should be translated correctly for language: "
                        + language
        );

        assertFalse(
                isMenuTextVisible("Clothes"),
                "English category should not remain visible after switching language"
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Hrvatski, Prijavite se",
            "Français, Connexion"
    })
    void shouldUpdateProductTitlesWhenLanguageIsChanged(
            String language,
            String expectedLoginText
    ) {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        String englishProductTitle =
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector("a.product-miniature__title")
                ).getText();

        homePage.switchLanguage(language);

        FrameUtils.switchToDefaultContent(driver);
        FrameUtils.switchToStoreFrame(driver);

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                expectedLoginText
        );

        String translatedProductTitle =
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector("a.product-miniature__title")
                ).getText();

        assertNotEquals(
                englishProductTitle,
                translatedProductTitle,
                "Product title should change after language switch"
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Hrvatski, Prijavite se",
            "Français, Connexion"
    })
    void shouldDisplayProductPricesCorrectlyAfterLanguageSwitch(
            String language,
            String expectedLoginText
    ) {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.switchLanguage(language);

        FrameUtils.switchToDefaultContent(driver);
        FrameUtils.switchToStoreFrame(driver);

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                expectedLoginText
        );

        assertTrue(
                WaitUtils.waitForVisible(
                        driver,
                        By.cssSelector("div.product-miniature__prices")
                ).isDisplayed(),
                "Product price should remain visible after language switch"
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Hrvatski, Prijavite se, Kontakt",
            "Français, Connexion, Contact"
    })
    void shouldDisplayLocalizedFooterElementsAfterLanguageSwitch(
            String language,
            String expectedLoginText,
            String footerText
    ) {

        HomePage homePage = new HomePage(driver);

        FrameUtils.switchToStoreFrame(driver);

        homePage.switchLanguage(language);

        FrameUtils.switchToDefaultContent(driver);
        FrameUtils.switchToStoreFrame(driver);

        WaitUtils.waitForText(
                driver,
                By.tagName("body"),
                expectedLoginText
        );

        assertTrue(
                isTextVisible(footerText),
                "Localized footer text should be visible"
        );
    }
}