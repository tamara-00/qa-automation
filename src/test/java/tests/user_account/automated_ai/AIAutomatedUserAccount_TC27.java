package tests.user_account.automated_ai;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.WaitUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIAutomatedUserAccount_TC27 extends BaseTest {

    private static final String EMAIL = "pub@prestashop.com";
    private static final String PASSWORD = "123456789";

    // TC.27 - User Account Logout
    @Test
    public void TC27_userAccount_logout() {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        AccountPage account = new AccountPage(driver);

        home.switchToStoreFrame();
        home.clickSignIn();
        login.login(EMAIL, PASSWORD);

        account.logout();

        home.switchToStoreFrame();

        WaitUtils.waitForText(driver, By.className("header-block__title"), "Sign in");

        boolean isSignInVisible = driver.findElement(By.className("header-block__title")).getText().contains("Sign in");
        assertTrue(isSignInVisible, "User is still logged in after logout");
    }

    @Test
    public void TC27_userAccount_logout_via_dropdown() {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        AccountPage account = new AccountPage(driver);

        home.switchToStoreFrame();
        home.clickSignIn();
        login.login(EMAIL, PASSWORD);

        driver.switchTo().defaultContent();
        WaitUtils.waitForPresence(driver, By.id("framelive"));
        home.switchToStoreFrame();

        account.logoutViaDropdown();

        driver.switchTo().defaultContent();
        WaitUtils.waitForPresence(driver, By.id("framelive"));
        home.switchToStoreFrame();

        boolean isLoggedOut = WaitUtils.waitForVisible(driver, By.className("header-block__title")).getText().contains("Sign in");

        assertTrue(isLoggedOut, "User is still logged in after logout via dropdown");
    }
}