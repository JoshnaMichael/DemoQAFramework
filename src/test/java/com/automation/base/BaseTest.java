package com.automation.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public WebDriver driver;

    // Create logger for this class
    private static final Logger log =
        LogManager.getLogger(BaseTest.class);

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setup() {
        log.info("========== TEST STARTING ==========");
        log.info("Setting up browser...");
        WebDriverManager.chromedriver()
                        .browserVersion("148").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(10));
        log.info("Browser opened successfully!");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            log.error("TEST FAILED: " + result.getName());
            log.error("Reason: " +
                result.getThrowable().getMessage());
            try {
                TakesScreenshot ts =
                    (TakesScreenshot) driver;
                File src = ts.getScreenshotAs(
                    OutputType.FILE);
                String path = "./screenshots/"
                            + result.getName()
                            + ".png";
                FileUtils.copyFile(src, new File(path));
                log.info("Screenshot saved: " + path);
            } catch (IOException e) {
                log.error("Screenshot failed: "
                    + e.getMessage());
            }
        } else if(result.getStatus() == ITestResult.SUCCESS) {
            log.info("TEST PASSED: " + result.getName());
            File screenshot = new File("./screenshots/"
                            + result.getName() + ".png");
            if(screenshot.exists()) {
                screenshot.delete();
                log.info("Screenshot deleted: "
                    + result.getName() + ".png");
            }
        }
        driver.quit();
        log.info("Browser closed!");
        log.info("========== TEST FINISHED ==========\n");
    }
}