package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Page_Objects.Login_Page;
import Page_Objects.CM_OrganizationFilter_Page;
import Utilities.ConfigReader;
import Utilities.Take_Screenshot;

public class CM_OrganizationFilter {

    WebDriver driver;
    XSSFWorkbook ExcelWBook;
    XSSFSheet loginSheet;

    @BeforeClass
    void setup() throws IOException {
        // Set Chrome preferences to access globally
        ChromeOptions options = new ChromeOptions();

        // Initialize WebDriver with ChromeOptions
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("implicit.wait.seconds"))));
        driver.manage().window().maximize();

        // Setup Excel file
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);
        ExcelWBook = new XSSFWorkbook(inputStream);
        loginSheet = ExcelWBook.getSheetAt(0);
    }

    @BeforeMethod
    void navigateToHomePage() throws InterruptedException {
        // Login before managing plans
        driver.get(ConfigReader.getProperty("login.url"));
        String username = loginSheet.getRow(5).getCell(0).toString();
        String password = loginSheet.getRow(5).getCell(1).toString();

        // Perform login
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("webdriver.wait.seconds"))));
        wait.until(ExpectedConditions.urlContains("home"));
    }

    @Test(priority = 1)
    void CM_ManageOrganization_Filter1() {
        CM_OrganizationFilter_Page manageOrg = new CM_OrganizationFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        SoftAssert soft = new SoftAssert();

        try {
            // Navigate to home page
            driver.get(ConfigReader.getProperty("home.url"));
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            manageOrg.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            soft.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getProperty("newtab.url")),
                    "New tab URL does not match expected");

            // Perform Manage org workflow
            manageOrg.clickManageOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.manage_org));
            Thread.sleep(2000);

            manageOrg.clickOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.org));
            Thread.sleep(2000);

            manageOrg.clickMoreFilters();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.more_filters));
            Thread.sleep(2000);

            manageOrg.clickStatusHeader();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.header_status));
            Thread.sleep(4000);

            manageOrg.selectActiveStatus();
            Thread.sleep(4000);
            
            //Click again to uncheck
            manageOrg.selectActiveStatus();
            Thread.sleep(3000);
            
            manageOrg.selectInactiveStatus();
            Thread.sleep(4000);
            
          //Click again to uncheck
            manageOrg.selectInactiveStatus();
            Thread.sleep(3000);
            
            manageOrg.selectDraftStatus();
            Thread.sleep(4000);
            
            soft.assertTrue(manageOrg.isStatusHeaderDisplayed(), "Status header is not displayed");
            soft.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
            soft.fail("Test failed due to exception: " + e.getMessage());
            soft.assertAll();
        }
    }
    
    
    @Test(priority = 2)
    void CM_ManageOrganization_Filter2() {
        CM_OrganizationFilter_Page manageOrg = new CM_OrganizationFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        SoftAssert soft = new SoftAssert();

        try {
            // Navigate to home page
            driver.get(ConfigReader.getProperty("home.url"));
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            manageOrg.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            soft.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getProperty("newtab.url")),
                    "New tab URL does not match expected");

            // Perform Manage org workflow
            manageOrg.clickManageOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.manage_org));
            Thread.sleep(2000);

            manageOrg.clickOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.org));
            Thread.sleep(2000);
            
            manageOrg.clickMoreFilters();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.more_filters));
            Thread.sleep(2000);

            manageOrg.clickSubscriptionHeader();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.header_subscription));
            Thread.sleep(2000);

            manageOrg.selectPremium();
            Thread.sleep(4000);
          //Click again to uncheck
            manageOrg.selectPremium();
            Thread.sleep(3000);
            
            manageOrg.selectFree();
            Thread.sleep(4000);
          //Click again to uncheck
            manageOrg.selectFree();
            Thread.sleep(3000);
            
            manageOrg.selectCustom();
            Thread.sleep(4000);
            
            soft.assertTrue(manageOrg.isStatusHeaderDisplayed(), "Status header is not displayed");

            soft.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
            soft.fail("Test failed due to exception: " + e.getMessage());
            soft.assertAll();
        }
    }

    @Test(priority = 3)
    void CM_ManageOrganization_Filter3() {
        CM_OrganizationFilter_Page manageOrg = new CM_OrganizationFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        SoftAssert soft = new SoftAssert();

        try {
            // Navigate to home page
            driver.get(ConfigReader.getProperty("home.url"));
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            manageOrg.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            soft.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getProperty("newtab.url")),
                    "New tab URL does not match expected");

            // Perform Manage org workflow
            manageOrg.clickManageOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.manage_org));
            Thread.sleep(2000);

            manageOrg.clickOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.org));
            Thread.sleep(2000);

            manageOrg.clickMoreFilters();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.more_filters));
            Thread.sleep(2000);

            manageOrg.clickOrganizationTypeHeader();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.header_organization_type));
            Thread.sleep(2000);
            
            manageOrg.selectSoftwareCompany();
            Thread.sleep(4000);
            
            soft.assertTrue(manageOrg.isStatusHeaderDisplayed(), "Status header is not displayed");

            soft.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
            soft.fail("Test failed due to exception: " + e.getMessage());
            soft.assertAll();
        }
    }
    
    @Test(priority = 4)
    void CM_ManageOrganization_Filter4() {
        CM_OrganizationFilter_Page manageOrg = new CM_OrganizationFilter_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        SoftAssert soft = new SoftAssert();

        try {
            // Navigate to home page
            driver.get(ConfigReader.getProperty("home.url"));
            wait.until(ExpectedConditions.urlContains("home"));

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            manageOrg.clickManageOrg();
            Thread.sleep(2000);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            soft.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getProperty("newtab.url")),
                    "New tab URL does not match expected");

            // Perform Manage org workflow
            manageOrg.clickManageOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.manage_org));
            Thread.sleep(2000);

            manageOrg.clickOrg();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.org));
            Thread.sleep(2000);

            manageOrg.clickMoreFilters();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.more_filters));
            Thread.sleep(3000);

            manageOrg.clickRecentlyAddedHeader();
            wait.until(ExpectedConditions.elementToBeClickable(manageOrg.header_recently_added));
            Thread.sleep(4000);

            soft.assertTrue(manageOrg.isStatusHeaderDisplayed(), "Status header is not displayed");

            soft.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
            soft.fail("Test failed due to exception: " + e.getMessage());
            soft.assertAll();
        }
    }

    @AfterMethod
    public void captureFailureScreenshot(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
        }

        try {
            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    driver.close();
                }
            }
            driver.switchTo().window(originalWindow);
            driver.get("about:blank");
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