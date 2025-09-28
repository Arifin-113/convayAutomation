package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import Page_Objects.CM_OrganizationEdit_Page;
import Utilities.Take_Screenshot;

public class CM_OrganizationEdit {

	WebDriver driver;
    XSSFWorkbook ExcelWBook;
    XSSFSheet ExcelWSheet;

    @BeforeClass
    void setup() throws IOException {
        // Set Chrome to access globally
        ChromeOptions options = new ChromeOptions();
      
        // Initialize WebDriver with ChromeOptions
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();

        // Setup Excel file
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);
        ExcelWBook = new XSSFWorkbook(inputStream);
        ExcelWSheet = ExcelWBook.getSheetAt(0); 
    }

    @BeforeMethod
    void navigateToHomePage() throws InterruptedException {
        // Login before managing plans
        driver.get("https://meet2.synesisit.info/sign-in");

        ExcelWSheet = ExcelWBook.getSheetAt(0); 

        // Read username and password from Excel
        String username = ExcelWSheet.getRow(5).getCell(0).toString();
        String password = ExcelWSheet.getRow(5).getCell(1).toString();

        // Perform login
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();
        Thread.sleep(4000); 
    }
/*
    @Test(priority = 2)
    void CM_OrganizationEdit2() throws InterruptedException {
    	CM_OrganizationEdit_Page manageOrg = new CM_OrganizationEdit_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased timeout

        try {
            // Navigate to home page
            driver.get("https://meet2.synesisit.info/home");
            Thread.sleep(2000); // Wait for page to load

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            managePlans.clickManageOrg();
            

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(3));

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

            // Verify new tab URL
            String newTabUrl = driver.getCurrentUrl();
            System.out.println("New tab URL: " + newTabUrl); // Debug print
            Assert.assertTrue(newTabUrl.contains("https://meet2.synesisit.info:85/"),
                    "New tab URL does not match expected: https://meet2.synesisit.info:85/");

            // Perform Manage Plans workflow
            manageOrg.clickManageOrg();
            Thread.sleep(2000);
            
            // Org Button Locator
            manageOrg.orgButton();
            Thread.sleep(4000);


            // Edit Button Locator
            manageOrg.editButton();
            Thread.sleep(2000);
            // typeChooseOrg
            manageOrg.typeChooseOrg();
            Thread.sleep(2000);
            
         // Search for the created plan
         			manageOrg.clicksetOrgName();
         			Thread.sleep(2000);

         			manageOrg.clearsetOrgName();

         			// Read plan name from Excel for Updating
         			ExcelWSheet = ExcelWBook.getSheet("CM_OrganizationEdit");
         			String setOrgName2 = ExcelWSheet.getRow(0).getCell(0).toString();
         			Thread.sleep(2000);

         			CM_OrganizationEdit_Page ep = new CM_OrganizationEdit_Page(driver);
         			ep.setOrgName(setOrgName2);
         			Thread.sleep(3000);
            

            // If Enter key doesn't submit the update, click Update button
            manageOrg.clickUpdateButton();
            Thread.sleep(2000);
            manageOrg.clickOkAfterUpdate();
            Thread.sleep(2000);

            // Switch back to original tab if needed
            driver.switchTo().window(originalWindow);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
  */  
    @Test(priority = 1)
    void CM_OrganizationEdit1() throws InterruptedException {
    	CM_OrganizationEdit_Page manageOrg = new CM_OrganizationEdit_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased timeout

        try {
            // Navigate to home page
            driver.get("https://meet2.synesisit.info/home");
            Thread.sleep(2000); // Wait for page to load

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
            System.out.println("Window handles: " + windowHandles);
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new tab: " + windowHandle);
                    break;
                }
            }

            // Verify new tab URL
            String newTabUrl = driver.getCurrentUrl();
            System.out.println("New tab URL: " + newTabUrl); // Debug print
            Assert.assertTrue(newTabUrl.contains("https://meet2.synesisit.info:85/"),
                    "New tab URL does not match expected: https://meet2.synesisit.info:85/");

            // Perform Manage Plans workflow
            manageOrg.clickManageOrg();
            Thread.sleep(2000);
            
            // Org Button Locator
            manageOrg.orgButton();
            Thread.sleep(3000);


            // Edit Button Locator
            manageOrg.editButton2();
            Thread.sleep(2000);
            // typeChooseOrg
            manageOrg.typeChooseOrg();
            Thread.sleep(2000);
            
            // Search for the created org
            manageOrg.clicksetOrgName();
         	Thread.sleep(2000);

         	manageOrg.clearsetOrgName();

         	// Read org name from Excel for Updating
         	ExcelWSheet = ExcelWBook.getSheet("CM_OrganizationEdit");
         	String setOrgName2 = ExcelWSheet.getRow(0).getCell(0).toString();
         	Thread.sleep(2000);

         	CM_OrganizationEdit_Page ep = new CM_OrganizationEdit_Page(driver);
         	ep.setOrgName(setOrgName2);
         	Thread.sleep(3000);
            

            // If Enter key doesn't submit the update, click Update button
         	manageOrg.clickUpdateButton();
            Thread.sleep(2000);
            manageOrg.clickOkAfterUpdate();
            Thread.sleep(2000);

            // Switch back to original tab if needed
            driver.switchTo().window(originalWindow);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
    
    
    @AfterMethod
    public void captureFailureScreenshot2(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
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