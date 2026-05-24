package tests.user_account.human_written;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.LoginPage;
import pages.AccountPage;
import utils.WaitUtils;
import org.openqa.selenium.By;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManualUserAccount_TC28 extends BaseTest {

    private static final String TEST_EMAIL = "pub@prestashop.com";
    private static final String TEST_PASS = "123456789";
    private static final String NEW_FIRSTNAME = "Jane";
    private static final String NEW_LASTNAME = "Doe";
    private static final String NEW_EMAIL = "jane.doe.test@gmaill.com";

    // TC.28 - Account Profile - Edit Personal Information
    @Test
    public void TC28_editAccountInformation() {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        AccountPage account = new AccountPage(driver);

        home.switchToStoreFrame();
        home.clickSignIn();
        login.login(TEST_EMAIL, TEST_PASS);
        driver.switchTo().defaultContent();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
        WaitUtils.waitForPresence(driver, By.id("framelive"));
        home.switchToStoreFrame();

        account.openInformationSection();
        account.updatePersonalInfo(NEW_FIRSTNAME, NEW_LASTNAME, NEW_EMAIL, TEST_PASS);
        assertTrue(account.getSuccessMessage().contains("Information successfully updated"),
                "TC.28 FAILED: Success message not displayed or incorrect after updating account information");
    }
}