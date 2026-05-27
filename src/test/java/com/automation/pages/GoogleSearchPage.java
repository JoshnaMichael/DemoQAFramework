package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchPage {

    WebDriver driver;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    By searchBox = By.xpath("//textarea[@name='q']");

    public void openGoogle() {
        driver.get("https://www.google.com");
    }

    public void searchFor(String keyword) {
        driver.findElement(searchBox).sendKeys(keyword);
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void waitForResults(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver,
            Duration.ofSeconds(10));
        wait.until(ExpectedConditions
            .titleContains(keyword));
    }
}