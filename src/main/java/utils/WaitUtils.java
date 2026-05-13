package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class WaitUtils {

    private static final int TIMEOUT = 15;

    public static WebElement waitForVisible(
            WebDriver driver,
            By locator
    ) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(TIMEOUT)
        ).until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        );
    }

    public static WebElement waitForClickable(
            WebDriver driver,
            By locator
    ) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(TIMEOUT)
        ).until(
                ExpectedConditions.elementToBeClickable(
                        locator
                )
        );
    }

    public static boolean waitForInvisible(
            WebDriver driver,
            By locator
    ) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(TIMEOUT)
        ).until(
                ExpectedConditions.invisibilityOfElementLocated(
                        locator
                )
        );
    }

    public static void jsClick(
            WebDriver driver,
            WebElement element
    ) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element
                );
    }

    public static void scrollToBottom(
            WebDriver driver
    ) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "window.scrollTo(0, document.body.scrollHeight);"
                );
    }

    public static void scrollIntoView(
            WebDriver driver,
            WebElement element
    ) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});",
                        element
                );
    }

    public static void robustClick(
            WebDriver driver,
            WebElement element
    ) {

        scrollIntoView(
                driver,
                element
        );

        try {

            element.click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            element
                    );
        }
    }

    public static void waitForCondition(
            WebDriver driver,
            Function<WebDriver, Boolean> condition
    ) {

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(20)
                );

        wait.until(condition);
    }

    public static void waitForText(
            WebDriver driver,
            By locator,
            String text
    ) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}