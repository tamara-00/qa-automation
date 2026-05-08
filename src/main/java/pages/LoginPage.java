package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    private By email = By.id("field-email");
    private By password = By.id("field-password");
    private By submit = By.id("submit-login");
    private By error = By.cssSelector(".alert-danger, .alert.alert-danger");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String userEmail, String userPassword) {
        WebElement emailField = WaitUtils.waitForVisible(driver, email);
        WebElement passwordField = WaitUtils.waitForVisible(driver, password);

        emailField.clear();
        emailField.sendKeys(userEmail);

        passwordField.clear();
        passwordField.sendKeys(userPassword);

        WaitUtils.waitForClickable(driver, submit).click();
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(error).size() > 0;
    }
}