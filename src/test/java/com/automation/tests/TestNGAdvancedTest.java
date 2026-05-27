package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.utils.RetryAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class TestNGAdvancedTest extends BaseTest {

    WebDriverWait wait;

    @BeforeMethod
    public void init() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Group: smoke — quick sanity check
    @Test(groups = {"smoke"})
    public void verifyDemoQATitle() {
        driver.get("https://demoqa.com");
        String title = driver.getTitle();
        System.out.println("Title: " + title);
        Assert.assertTrue(title.contains("demosite"));
    }

    // Group: smoke — another quick check
    @Test(groups = {"smoke"})
    public void verifyTextBoxPageLoads() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        Thread.sleep(2000);
        WebElement heading = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.tagName("h1")));
        Assert.assertEquals(heading.getText(), "Text Box");
        System.out.println("Text Box page loaded!");
    }

    // Group: regression — detailed test
    @Test(groups = {"regression"})
    public void verifyButtonsPageLoads() throws InterruptedException {
        driver.get("https://demoqa.com/buttons");
        Thread.sleep(2000);
        WebElement heading = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.tagName("h1")));
        Assert.assertEquals(heading.getText(), "Buttons");
        System.out.println("Buttons page loaded!");
    }

    // Group: both smoke and regression + Retry
    @Test(groups = {"smoke", "regression"},
          retryAnalyzer = RetryAnalyzer.class)
    public void verifyFormsPageLoads() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        Thread.sleep(2000);
        WebElement fullNameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("userName")));
        Assert.assertNotNull(fullNameInput);
        System.out.println("Forms page and input verified!");
    }

    // Retry test — will retry if fails
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testWithRetry() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        Thread.sleep(2000);
        WebElement btn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("alertButton")));
        Assert.assertNotNull(btn);
        System.out.println("Alert button found — retry test passed!");
    }
}