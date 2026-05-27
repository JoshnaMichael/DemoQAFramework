package com.automation.tests;

import com.automation.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class ActionsTest extends BaseTest {

    private static final Logger log =
        LogManager.getLogger(ActionsTest.class);

    Actions actions;
    WebDriverWait wait;

    @BeforeMethod
    public void initActions() {
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        log.info("Actions and Wait initialized!");
    }

    @Test
    public void testRightClick() throws InterruptedException {
        log.info("Starting testRightClick...");
        driver.get("https://demoqa.com/buttons");
        log.debug("Navigated to buttons page");
        Thread.sleep(3000);
        WebElement btn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("rightClickBtn")));
        log.debug("Right click button found");
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView(true);", btn);
        Thread.sleep(1000);
        actions.contextClick(btn).perform();
        log.debug("Right click performed");
        Thread.sleep(1000);
        WebElement msg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("rightClickMessage")));
        Assert.assertEquals(msg.getText(),
            "You have done a right click");
        log.info("testRightClick PASSED!");
    }

    @Test
    public void testDoubleClick() throws InterruptedException {
        log.info("Starting testDoubleClick...");
        driver.get("https://demoqa.com/buttons");
        log.debug("Navigated to buttons page");
        Thread.sleep(3000);
        WebElement btn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("doubleClickBtn")));
        log.debug("Double click button found");
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView(true);", btn);
        Thread.sleep(1000);
        actions.doubleClick(btn).perform();
        log.debug("Double click performed");
        Thread.sleep(1000);
        WebElement msg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("doubleClickMessage")));
        Assert.assertEquals(msg.getText(),
            "You have done a double click");
        log.info("testDoubleClick PASSED!");
    }

    @Test
    public void testDragAndDrop() throws InterruptedException {
        log.info("Starting testDragAndDrop...");
        driver.get("https://demoqa.com/droppable");
        log.debug("Navigated to droppable page");
        Thread.sleep(3000);
        WebElement source = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("draggable")));
        WebElement target = driver.findElement(
            By.id("droppable"));
        log.debug("Source and target elements found");
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView(true);", source);
        Thread.sleep(1000);
        actions.clickAndHold(source)
               .moveToElement(target)
               .pause(Duration.ofMillis(500))
               .release()
               .perform();
        log.debug("Drag and drop performed");
        Thread.sleep(1000);
        Assert.assertEquals(target.getText(), "Dropped!");
        log.info("testDragAndDrop PASSED!");
    }
}