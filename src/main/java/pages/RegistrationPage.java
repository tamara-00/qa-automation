package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class RegistrationPage {

    private WebDriver driver;

    private By createAccountLink = By.cssSelector(".no-account a");

    private By passwordField = By.id("field-password");

    private By passwordHint = By.xpath("//span[contains(text(),'8 characters')] | //button[@data-action='show-password']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateAccountLink() {
        WaitUtils.waitForClickable(driver, createAccountLink).click();
    }

    public void focusPasswordField() {
        WaitUtils.waitForVisible(driver, passwordField).click();
    }

    public boolean arePasswordRequirementsDisplayed() {
        try {
            WebElement hint = WaitUtils.waitForVisible(driver, passwordHint);
            return hint.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}