package com.automation.locators;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LocatorExtractor {

    WebDriver driver;

    // DemoQA pages to extract locators from
    List<String> pages = new ArrayList<>(Arrays.asList(
        "https://demoqa.com/text-box",
        "https://demoqa.com/check-box",
        "https://demoqa.com/radio-button",
        "https://demoqa.com/webtables",
        "https://demoqa.com/buttons",
        "https://demoqa.com/links"
    ));

    @BeforeTest
    public void setup() {
        System.out.println("=== Setting up browser ===");
        WebDriverManager.chromedriver()
                        .browserVersion("148").setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches",
            Arrays.asList("enable-automation"));

        driver = new ChromeDriver(options);
        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(10));
        System.out.println("=== Browser opened ===");
    }

    @AfterTest
    public void teardown() {
        if(driver != null) {
            driver.quit();
            System.out.println("=== Browser closed ===");
        }
    }

    @Test
    public void extractLocators() throws IOException {

        // Create Excel workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Locators");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Page");
        header.createCell(1).setCellValue("Tag Name");
        header.createCell(2).setCellValue("ID");
        header.createCell(3).setCellValue("Name");
        header.createCell(4).setCellValue("Class");
        header.createCell(5).setCellValue("Text");
        header.createCell(6).setCellValue("XPath");
        header.createCell(7).setCellValue("CSS Selector");

        int rowNum = 1;

        // Loop through each page
        for(String pageUrl : pages) {

            System.out.println("Extracting from: " + pageUrl);
            driver.get(pageUrl);

            // Wait for page to load
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            // Find ALL elements on page
            List<WebElement> elements = 
                driver.findElements(By.xpath("//*"));

            System.out.println("Found " + elements.size() 
                + " elements on " + pageUrl);

            // Extract locators from each element
            for(WebElement element : elements) {
                try {
                    // Get element properties
                    String tagName = element.getTagName();
                    String id      = element.getAttribute("id");
                    String name    = element.getAttribute("name");
                    String classe  = element.getAttribute("class");
                    String text    = element.getText();

                    // Skip html, head, body, script tags
                    if(tagName.equals("html") ||
                       tagName.equals("head") ||
                       tagName.equals("body") ||
                       tagName.equals("script") ||
                       tagName.equals("style")) {
                        continue;
                    }

                    // Generate XPath
                    String xpath = generateXPath(
                        element, tagName, id, name, text);

                    // Generate CSS Selector
                    String css = generateCSS(
                        tagName, id, classe);

                    // Write to Excel row
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(pageUrl);
                    row.createCell(1).setCellValue(
                        tagName != null ? tagName : "");
                    row.createCell(2).setCellValue(
                        id != null ? id : "");
                    row.createCell(3).setCellValue(
                        name != null ? name : "");
                    row.createCell(4).setCellValue(
                        classe != null ? classe : "");
                    row.createCell(5).setCellValue(
                        text != null && text.length() > 50 
                        ? text.substring(0, 50) : 
                        text != null ? text : "");
                    row.createCell(6).setCellValue(xpath);
                    row.createCell(7).setCellValue(css);

                } catch(StaleElementReferenceException e) {
                    System.out.println(
                        "Stale element skipped!");
                    continue;
                }
            }
        }

        // Auto size columns
        for(int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        // Save Excel file
        String filePath = "./locators/DemoQA_Locators.xlsx";
        new java.io.File("./locators").mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        workbook.close();
        fos.close();

        System.out.println("=== Locators saved to: " 
            + filePath + " ===");
    }

    // Generate XPath from element properties
    private String generateXPath(WebElement element,
        String tagName, String id, 
        String name, String text) {

        if(id != null && !id.isEmpty()) {
            return "//" + tagName + "[@id='" + id + "']";
        } else if(name != null && !name.isEmpty()) {
            return "//" + tagName + 
                   "[@name='" + name + "']";
        } else if(text != null && !text.isEmpty() 
                  && text.length() < 30) {
            return "//" + tagName + 
                   "[contains(text(),'" + text + "')]";
        } else {
            return "//" + tagName;
        }
    }

    // Generate CSS Selector from element properties
    private String generateCSS(String tagName,
        String id, String classe) {

        if(id != null && !id.isEmpty()) {
            return tagName + "#" + id;
        } else if(classe != null && !classe.isEmpty()) {
            // Take first class only
            String firstClass = classe.split(" ")[0];
            return tagName + "." + firstClass;
        } else {
            return tagName;
        }
    }
}