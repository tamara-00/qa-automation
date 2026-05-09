package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import java.time.Duration;
import java.util.List;

public class RegistrationPage {
    private WebDriver driver;

    private By createAccountLink = By.cssSelector("a[data-link-action='display-register-form']");
    private By socialTitleMrs = By.id("field-id_gender_2");
    private By firstNameField = By.id("field-firstname");
    private By lastNameField = By.id("field-lastname");
    private By emailField = By.id("field-email");
    private By passwordField = By.id("field-password");
    private By birthdayField = By.id("field-birthday");
    private By psgdprCheckbox = By.id("field-psgdpr");
    private By customerPrivacyCheckbox = By.id("field-customer_privacy");

    private By passwordPolicySection = By.cssSelector(".password-policy-input-container");
    private By passwordStrengthText = By.cssSelector(".password-strength-text");
    private By specificRequirements = By.cssSelector(".password-requirements-list li");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateAccountLink() {
        WebElement link = WaitUtils.waitForVisible(driver, createAccountLink);
        utils.WaitUtils.scrollIntoView(driver, link);
        utils.WaitUtils.jsClick(driver, link);
    }

    public void fillPersonalDetails(String fName, String lName, String email, String bday) {
        WaitUtils.waitForClickable(driver, socialTitleMrs).click();
        driver.findElement(firstNameField).sendKeys(fName);
        driver.findElement(lastNameField).sendKeys(lName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(birthdayField).sendKeys(bday);

        utils.WaitUtils.jsClick(driver, driver.findElement(psgdprCheckbox));
        utils.WaitUtils.jsClick(driver, driver.findElement(customerPrivacyCheckbox));
    }

    public void typePasswordRealTime(String password) {
        WebElement pField = WaitUtils.waitForVisible(driver, passwordField);
        pField.clear();
        for (char c : password.toCharArray()) {
            pField.sendKeys(String.valueOf(c));
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
    }

    public String getPasswordStrengthResult() {
        try {
            Thread.sleep(500);
            return WaitUtils.waitForVisible(driver, passwordStrengthText).getText();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public void printFailedRequirements() {
        List<WebElement> requirements = driver.findElements(specificRequirements);
        System.out.println("Status of specific requirements:");
        for (WebElement req : requirements) {
            String status = req.getAttribute("class");
            String icon = (status.contains("success") || status.contains("valid")) ? "[✔]" : "[✘]";
            System.out.println("   " + icon + " " + req.getText());
        }
    }

    public boolean areRequirementsVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordPolicySection)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}