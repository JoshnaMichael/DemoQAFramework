package com.automation.tests;

import com.automation.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class JavaScriptExecutorTest extends BaseTest {

    JavascriptExecutor js;
    WebDriverWait wait;

    @BeforeMethod
    public void init() {
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testScrollToBottom() throws InterruptedException {
        driver.get("https://demoqa.com/elements");
        Thread.sleep(2000);
        js.executeScript(
            "window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
        System.out.println("Scrolled to bottom!");
    }

    @Test
    public void testScrollToElement() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        Thread.sleep(2000);
        WebElement btn = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("submit")));
        js.executeScript(
            "arguments[0].scrollIntoView(true);", btn);
        Thread.sleep(1000);
        System.out.println("Scrolled to submit button!");
    }

    @Test
    public void testJSClick() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        Thread.sleep(2000);
        WebElement fullName = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("userName")));
        js.executeScript(
            "arguments[0].value='Joshna Michael';", fullName);
        WebElement email = driver.findElement(
            By.id("userEmail"));
        js.executeScript(
            "arguments[0].value='josh@test.com';", email);
        WebElement btn = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView(true);", btn);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", btn);
        Thread.sleep(1000);
        System.out.println("Form filled and submitted via JS!");
    }

    @Test
    public void testGetPageInfo() {
        driver.get("https://demoqa.com/text-box");
        String title = (String) js.executeScript(
            "return document.title");
        String url = (String) js.executeScript(
            "return window.location.href");
        System.out.println("Title: " + title);
        System.out.println("URL: " + url);
        Assert.assertNotNull(title);
        Assert.assertTrue(url.contains("demoqa"));
    }

    @Test
    public void testHighlightElement() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        Thread.sleep(2000);
        WebElement input = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("userName")));
        js.executeScript(
            "arguments[0].style.border='3px solid red'", input);
        Thread.sleep(1000);
        js.executeScript(
            "arguments[0].style.border=''", input);
        System.out.println("Element highlighted!");
    }
}