package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.FrameUtils;
import utils.WaitUtils;

import java.time.Duration;

public class AccountPage {

    private WebDriver driver;

    private By signOut = By.xpath("//a[contains(.,'Sign out')]");
    private By signIn = By.xpath("//a[contains(.,'Sign in')]");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoggedIn() {
        return driver.findElements(signOut).size() > 0
                || driver.getPageSource().contains("Sign out");
    }

    public void logout() {
        FrameUtils.switchToStoreFrame(driver);

        WaitUtils.scrollToBottom(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By signOutLocator = By.xpath("//a[contains(normalize-space(),'Sign out')]");

        WebElement signOutButton = wait.until(ExpectedConditions.presenceOfElementLocated(signOutLocator));

        WaitUtils.scrollIntoView(driver, signOutButton);

        WaitUtils.jsClick(driver, signOutButton);

        driver.switchTo().defaultContent();
    }

    public boolean isLoggedOut() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        FrameUtils.switchToStoreFrame(driver);

        return driver.getPageSource().contains("Sign in");
    }
}