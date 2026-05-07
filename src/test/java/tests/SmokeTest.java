package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void openWebsite() {
        driver.get("https://demo.prestashop.com/");
        System.out.println(driver.getTitle());
    }
}