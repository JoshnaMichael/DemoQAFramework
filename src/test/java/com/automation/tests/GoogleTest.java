package com.automation.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleTest {

    public WebDriver driver;
    By searchBox = By.xpath("//textarea[@name='q']");

    @BeforeMethod
    public void setup() {
        System.out.println("=== SETUP CALLED ===");
        WebDriverManager.chromedriver()
                        .browserVersion("148").setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches",
            Arrays.asList("enable-automation"));

        driver = new ChromeDriver(options);
        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(10));
        System.out.println("=== BROWSER OPENED ===");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            try {
                TakesScreenshot ts =
                    (TakesScreenshot) driver;
                File src = ts.getScreenshotAs(
                    OutputType.FILE);
                String path = "./screenshots/"
                            + result.getName()
                            + ".png";
                FileUtils.copyFile(src, new File(path));
                System.out.println("Screenshot: " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        driver.quit();
        System.out.println("=== BROWSER CLOSED ===");
    }

    @Test(groups = {"smoke", "regression"})
    public void verifyGoogleTitle() {
        driver.get("https://www.google.com");

        WebDriverWait wait = new WebDriverWait(driver,
            Duration.ofSeconds(15));
        wait.until(ExpectedConditions.titleIs("Google"));

        String title = driver.getTitle();
        System.out.println("Title is: " + title);
        Assert.assertEquals(title, "Google");
        System.out.println("Title verified!");
    }

    @Test(groups = {"regression"})
    public void verifySearchResults() {
        driver.get("https://www.google.com");

        WebDriverWait wait = new WebDriverWait(driver,
            Duration.ofSeconds(15));
        wait.until(ExpectedConditions
            .elementToBeClickable(searchBox));

        driver.findElement(searchBox)
              .sendKeys("Selenium WebDriver");
        driver.findElement(searchBox)
              .sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions
            .titleContains("Selenium"));

        String title = driver.getTitle();
        System.out.println("Results Title: " + title);
        Assert.assertTrue(title.contains("Selenium"));
        System.out.println("Search verified!");
    }

    @Test(groups = {"regression"})
    public void failingTest() {
        driver.get("https://www.google.com");

        WebDriverWait wait = new WebDriverWait(driver,
            Duration.ofSeconds(15));
        wait.until(ExpectedConditions.titleIs("Google"));

        String title = driver.getTitle();
        Assert.assertEquals(title, "Wrong Title Here");
    }

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return new Object[][] {
            {"Selenium WebDriver"},
            {"Java Programming"},
            {"TestNG Framework"}
        };
    }

    @Test(groups = {"smoke", "regression"},
          dataProvider = "searchData")
    public void searchWithMultipleKeywords(String keyword) {
        driver.get("https://www.google.com");

        WebDriverWait wait = new WebDriverWait(driver,
            Duration.ofSeconds(15));
        wait.until(ExpectedConditions
            .elementToBeClickable(searchBox));

        driver.findElement(searchBox).sendKeys(keyword);
        driver.findElement(searchBox).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions
            .titleContains(keyword));

        String title = driver.getTitle();
        System.out.println("Searched: " + keyword +
                           " → Title: " + title);
        Assert.assertTrue(title.contains(keyword));
    }
}