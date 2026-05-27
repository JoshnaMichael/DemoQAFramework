package com.automation.tests;

import com.automation.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Set;

public class WindowsAndAlertsTest extends BaseTest {

    WebDriverWait wait;

    @BeforeMethod
    public void init() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Test 1 — Simple Alert (just OK button)
    @Test
    public void testSimpleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        Thread.sleep(2000);

        // Click button that triggers simple alert
        WebElement alertBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("alertButton")));
        alertBtn.click();
        Thread.sleep(1000);

        // Switch to alert and read message
        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        System.out.println("Alert message: " + message);
        Assert.assertEquals(message, "You clicked a button");

        // Click OK to dismiss
        alert.accept();
        System.out.println("Simple alert accepted!");
    }

    // Test 2 — Confirm Alert (OK and Cancel)
    @Test
    public void testConfirmAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        Thread.sleep(2000);

        // Click button that triggers confirm alert
        WebElement confirmBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("confirmButton")));
        confirmBtn.click();
        Thread.sleep(1000);

        // Switch to alert
        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        System.out.println("Confirm message: " + message);
        Assert.assertEquals(message, "Do you confirm action?");

        // Click OK
        alert.accept();
        Thread.sleep(500);

        // Verify result text on page
        String result = driver.findElement(
            By.id("confirmResult")).getText();
        System.out.println("Result: " + result);
        Assert.assertTrue(result.contains("Ok"));
    }

    // Test 3 — Prompt Alert (has text input)
    @Test
    public void testPromptAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        Thread.sleep(2000);

        // Click button that triggers prompt alert
        WebElement promptBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("promtButton")));
        promptBtn.click();
        Thread.sleep(1000);

        // Switch to alert and type text
        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        System.out.println("Prompt message: " + message);

        // Type into the prompt
        alert.sendKeys("Joshna");
        Thread.sleep(500);

        // Click OK
        alert.accept();
        Thread.sleep(500);

        // Verify the typed name appears on page
        String result = driver.findElement(
            By.id("promptResult")).getText();
        System.out.println("Prompt result: " + result);
        Assert.assertTrue(result.contains("Joshna"));
    }

    // Test 4 — New Browser Tab/Window
    @Test
    public void testNewWindow() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        Thread.sleep(2000);

        // Save main window handle
        String mainWindow = driver.getWindowHandle();
        System.out.println("Main window: " + mainWindow);

        // Click button that opens new window
        WebElement newWindowBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("windowButton")));
        newWindowBtn.click();
        Thread.sleep(2000);

        // Get all window handles
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("Total windows: " + allWindows.size());

        // Switch to new window
        for(String handle : allWindows) {
            if(!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Verify new window content
        String newWindowText = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("sampleHeading"))).getText();
        System.out.println("New window text: " + newWindowText);
        Assert.assertEquals(newWindowText, "This is a sample page");

        // Close new window
        driver.close();
        System.out.println("New window closed!");

        // Switch back to main window
        driver.switchTo().window(mainWindow);
        System.out.println("Back to main window!");
    }

    // Test 5 — New Browser Tab
    @Test
    public void testNewTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        Thread.sleep(2000);

        // Save main window
        String mainWindow = driver.getWindowHandle();

        // Click new tab button
        WebElement newTabBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("tabButton")));
        newTabBtn.click();
        Thread.sleep(2000);

        // Switch to new tab
        Set<String> allWindows = driver.getWindowHandles();
        for(String handle : allWindows) {
            if(!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        String tabText = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("sampleHeading"))).getText();
        System.out.println("New tab text: " + tabText);
        Assert.assertEquals(tabText, "This is a sample page");

        // Close tab and switch back
        driver.close();
        driver.switchTo().window(mainWindow);
        System.out.println("Back to main window!");
    }
}