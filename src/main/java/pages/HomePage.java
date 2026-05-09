package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.FrameUtils;
import utils.WaitUtils;

public class HomePage {

    private WebDriver driver;

    private By signInButton = By.cssSelector("a[href*='login'], a[href*='my-account']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void switchToStoreFrame() {
        FrameUtils.switchToStoreFrame(driver);
    }

    public void clickSignIn() {
        WaitUtils.waitForClickable(driver, signInButton).click();
    }
}