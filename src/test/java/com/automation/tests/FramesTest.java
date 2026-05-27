package com.automation.tests;

import com.automation.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class FramesTest extends BaseTest {

    WebDriverWait wait;

    @BeforeMethod
    public void init() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Test 1 — Switch into frame by ID and read text
    @Test
    public void testSwitchToFrameById() throws InterruptedException {
        driver.get("https://demoqa.com/frames");
        Thread.sleep(2000);

        // Without switching — this would throw NoSuchElementException
        // Switch into the first frame by ID
        driver.switchTo().frame("frame1");

        // Now we can see elements inside the frame
        WebElement heading = driver.findElement(By.id("sampleHeading"));
        String text = heading.getText();
        System.out.println("Frame 1 text: " + text);
        Assert.assertEquals(text, "This is a sample page");

        // ALWAYS switch back after done
        driver.switchTo().defaultContent();
        System.out.println("Switched back to main page!");
    }

    // Test 2 — Switch into second frame
    @Test
    public void testSwitchToSecondFrame() throws InterruptedException {
        driver.get("https://demoqa.com/frames");
        Thread.sleep(2000);

        // Switch into second frame
        driver.switchTo().frame("frame2");

        WebElement heading = driver.findElement(By.id("sampleHeading"));
        String text = heading.getText();
        System.out.println("Frame 2 text: " + text);
        Assert.assertEquals(text, "This is a sample page");

        driver.switchTo().defaultContent();
        System.out.println("Switched back to main page!");
    }

    // Test 3 — Switch by WebElement (most reliable way)
    @Test
    public void testSwitchByWebElement() throws InterruptedException {
        driver.get("https://demoqa.com/frames");
        Thread.sleep(2000);

        // Find the frame element first
        WebElement frameElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("frame1")));

        // Switch using the element
        driver.switchTo().frame(frameElement);

        String text = driver.findElement(
            By.id("sampleHeading")).getText();
        System.out.println("Text via WebElement switch: " + text);
        Assert.assertNotNull(text);

        driver.switchTo().defaultContent();
    }

    // Test 4 — Nested frames
    @Test
    public void testNestedFrames() throws InterruptedException {
        driver.get("https://demoqa.com/nestedframes");
        Thread.sleep(2000);

        // Switch into outer frame
        driver.switchTo().frame("frame1");
        System.out.println("Switched to outer frame!");

        // Read outer frame body text
        String outerText = driver.findElement(
            By.tagName("body")).getText();
        System.out.println("Outer frame text: " + outerText);

        // Switch into inner frame (nested inside outer)
        driver.switchTo().frame(0);
        System.out.println("Switched to inner frame!");

        String innerText = driver.findElement(
            By.tagName("body")).getText();
        System.out.println("Inner frame text: " + innerText);

        // Come back out step by step
        driver.switchTo().parentFrame(); // back to outer frame
        driver.switchTo().defaultContent(); // back to main page
        System.out.println("Back to main page!");
    }
}