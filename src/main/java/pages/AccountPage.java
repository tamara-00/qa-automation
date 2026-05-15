package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.FrameUtils;
import utils.WaitUtils;

import java.time.Duration;

public class AccountPage {

    private WebDriver driver;

    private By signOutButton = By.cssSelector("a[href*='mylogout']");
    private By userProfileName = By.cssSelector(".header-block__title");
    private By firstNameField = By.id("field-firstname");
    private By lastNameField = By.id("field-lastname");
    private By emailField = By.id("field-email");
    private By passwordField = By.id("field-password");
    private By saveButton = By.cssSelector("button[data-link-action='save-customer']");
    private By successAlert = By.cssSelector(".alert-success");
    private By psgdprCheckbox = By.id("field-psgdpr");
    private By customerPrivacyCheckbox = By.id("field-customer_privacy");
    private By signOutXPath = By.xpath("//footer//a[contains(.,'Sign out')]");
    private By profileMenuButton = By.id("userMenuButton");
    private By informationLink = By.cssSelector("a[href*='identity']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoggedIn() {
        try {
            WebElement logoutBtn = WaitUtils.waitForPresence(driver, signOutXPath);
            WaitUtils.scrollIntoView(driver, logoutBtn);
            return logoutBtn.isDisplayed();
        } catch (Exception e) {
            System.out.println("Sign out button not found in footer: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        FrameUtils.switchToStoreFrame(driver);
        WaitUtils.scrollToBottom(driver);

        WebElement signOutButton = WaitUtils.waitForPresence(driver, By.xpath("//a[contains(normalize-space(),'Sign out')]"));

        WaitUtils.scrollIntoView(driver, signOutButton);
        WaitUtils.jsClick(driver, signOutButton);

        driver.switchTo().defaultContent();
    }

    public void logoutViaDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement profile = wait.until(ExpectedConditions.presenceOfElementLocated(userProfileName));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profile);

        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(signOutButton));
        logoutBtn.click();
    }

    public void openInformationSection() {
        WebElement menu = WaitUtils.waitForClickable(driver, profileMenuButton);
        WaitUtils.robustClick(driver, menu);

        WebElement info = WaitUtils.waitForPresence(driver, informationLink);

        WaitUtils.jsClick(driver, info);
    }

    public void updatePersonalInfo(String fName, String lName, String email, String currentPassword) {
        WebElement fn = WaitUtils.waitForVisible(driver, firstNameField);
        fn.clear();
        fn.sendKeys(fName);

        WebElement ln = driver.findElement(lastNameField);
        ln.clear();
        ln.sendKeys(lName);

        WebElement em = driver.findElement(emailField);
        em.clear();
        em.sendKeys(email);

        driver.findElement(passwordField).sendKeys(currentPassword);

        WaitUtils.jsClick(driver, driver.findElement(psgdprCheckbox));
        WaitUtils.jsClick(driver, driver.findElement(customerPrivacyCheckbox));

        WebElement saveBtn = driver.findElement(saveButton);
        WaitUtils.scrollIntoView(driver, saveBtn);
        WaitUtils.jsClick(driver, saveBtn);
    }

    public String getSuccessMessage() {
        return WaitUtils.waitForVisible(driver, successAlert).getText();
    }
}