package tests.user_account;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutTest extends BaseTest {

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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        boolean isSignInVisible = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("header-block__title"), "Sign in"
        ));

        assertTrue(isSignInVisible, "TC.27 FAILED: User is still logged in after logout");
    }
}