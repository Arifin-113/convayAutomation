package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Page_Objects.Login_Page;
import Page_Objects.CM_ManagePlansFilter_Page;
import Utilities.Take_Screenshot;

public class CM_ManagePlansFilter {

    WebDriver driver;
    XSSFWorkbook ExcelWBook;
    XSSFSheet ExcelWSheet;

    @BeforeClass
    void setup() throws IOException {
        // Set Chrome preferences to allow microphone access globally
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> contentSettings = new HashMap<>();
        HashMap<String, Object> profile = new HashMap<>();
        HashMap<String, Object> prefs = new HashMap<>();

        // Initialize WebDriver with ChromeOptions
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();

        // Setup Excel file
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);
        ExcelWBook = new XSSFWorkbook(inputStream);
        ExcelWSheet = ExcelWBook.getSheetAt(3); // Sheet for setup
    }

    @BeforeMethod
    void navigateToHomePage() {
        // Login before managing plans
        driver.get("https://meet2.synesisit.info/sign-in");

        ExcelWSheet = ExcelWBook.getSheetAt(0); // Using sheet 0 as requested

        // Read username and password from Excel
        String username = ExcelWSheet.getRow(5).getCell(0).toString();
        String password = ExcelWSheet.getRow(5).getCell(1).toString();

        // Perform login
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();

        // Wait for login to complete
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("home"));
    }

    @Test(priority = 1)
    void testManagePlans() {
        CM_ManagePlansFilter_Page managePlans = new CM_ManagePlansFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout

        try {
            // Navigate to home page
            driver.get("https://meet2.synesisit.info/home");
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            managePlans.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            System.out.println("Window handles: " + windowHandles);
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            // Wait for the new tab to fully load
            wait.until(ExpectedConditions.urlContains("https://meet2.synesisit.info:85/"));

            // Verify new tab URL
            String newTabUrl = driver.getCurrentUrl();
            System.out.println("New tab URL: " + newTabUrl);
            Assert.assertTrue(newTabUrl.contains("https://meet2.synesisit.info:85/"),
                    "New tab URL does not match expected: https://meet2.synesisit.info:85/");

            // Perform Manage Plans workflow
            managePlans.clickManagePlans();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Manage Plans']")));
            Thread.sleep(2000);

            // Apply filters and verify
            managePlans.clickFiltersButton();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Filters']")));
            Thread.sleep(2000);

            managePlans.clickFiltersButton2();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']")));
            Thread.sleep(2000);

            managePlans.selectDraftStatus();
            Assert.assertTrue(managePlans.isStatusHeaderDisplayed(), "Status header is not displayed");
            Thread.sleep(4000);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    void testManagePlans2() {
        CM_ManagePlansFilter_Page managePlans = new CM_ManagePlansFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout

        try {
            // Navigate to home page
            driver.get("https://meet2.synesisit.info/home");
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            managePlans.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            System.out.println("Window handles: " + windowHandles);
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            // Wait for the new tab to fully load
            wait.until(ExpectedConditions.urlContains("https://meet2.synesisit.info:85/"));

            // Verify new tab URL
            String newTabUrl = driver.getCurrentUrl();
            System.out.println("New tab URL: " + newTabUrl);
            Assert.assertTrue(newTabUrl.contains("https://meet2.synesisit.info:85/"),
                    "New tab URL does not match expected: https://meet2.synesisit.info:85/");

            // Perform Manage Plans workflow
            managePlans.clickManagePlans();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Manage Plans']")));
            Thread.sleep(2000);

            // Apply filters and verify
            managePlans.clickFiltersButton();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Filters']")));
            Thread.sleep(2000);

            managePlans.clickFiltersButton2();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']")));
            Thread.sleep(2000);

            managePlans.selectInactiveStatus();
            Assert.assertTrue(managePlans.isStatusHeaderDisplayed(), "Status header is not displayed");
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    void testManagePlans3() {
        CM_ManagePlansFilter_Page managePlans = new CM_ManagePlansFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout

        try {
            // Navigate to home page
            driver.get("https://meet2.synesisit.info/home");
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            managePlans.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            System.out.println("Window handles: " + windowHandles);
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            // Wait for the new tab to fully load
            wait.until(ExpectedConditions.urlContains("https://meet2.synesisit.info:85/"));

            // Verify new tab URL
            String newTabUrl = driver.getCurrentUrl();
            System.out.println("New tab URL: " + newTabUrl);
            Assert.assertTrue(newTabUrl.contains("https://meet2.synesisit.info:85/"),
                    "New tab URL does not match expected: https://meet2.synesisit.info:85/");

            // Perform Manage Plans workflow
            managePlans.clickManagePlans();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Manage Plans']")));
            Thread.sleep(2000);

            // Apply filters and verify
            managePlans.clickFiltersButton();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Filters']")));
            Thread.sleep(2000);

            managePlans.clickFiltersButton2();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']")));
            Thread.sleep(2000);

            managePlans.selectActiveStatus();
            Assert.assertTrue(managePlans.isStatusHeaderDisplayed(), "Status header is not displayed");
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void captureFailureScreenshot(ITestResult result) throws IOException {
        // Capture screenshot on failure
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
        }

        // Reset browser state: close all tabs except the original
        try {
            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    driver.close();
                }
            }
            driver.switchTo().window(originalWindow);
            driver.get("about:blank"); // Navigate to a blank page to reset state
        } catch (Exception e) {
            System.out.println("Failed to reset browser state: " + e.getMessage());
        }
    }

    @AfterClass
    void teardown() throws IOException {
        if (ExcelWBook != null) {
            ExcelWBook.close();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}