package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.testng.asserts.SoftAssert;

import Page_Objects.Login_Page;
import Page_Objects.CM_OrganizationDraft_Page;
import Page_Objects.CM_OrganizationEdit_Page;
import Utilities.ConfigReader;
import Utilities.Take_Screenshot;

public class CM_OrganizationDraft {

    WebDriver driver;
    XSSFWorkbook ExcelWBook;
    XSSFSheet ExcelWSheet;

    @BeforeClass
    void setup() throws IOException {
        // Set Chrome preferences to access globally
        ChromeOptions options = new ChromeOptions();

        // Initialize WebDriver with ChromeOptions
        driver = new ChromeDriver(options);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("implicit.wait.seconds"))));
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();

        // Setup Excel file
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);
        ExcelWBook = new XSSFWorkbook(inputStream);
        ExcelWSheet = ExcelWBook.getSheetAt(3); 
    }

    @BeforeMethod
    void navigateToHomePage() throws InterruptedException {
        // Login before managing plans
    	driver.get(ConfigReader.getProperty("login.url")); // Use config.properties
        ExcelWSheet = ExcelWBook.getSheetAt(0); 
        // Read username and password from Excel
        String username = ExcelWSheet.getRow(5).getCell(0).toString();
        String password = ExcelWSheet.getRow(5).getCell(1).toString();

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
    void CM_Organization_Draft() throws InterruptedException {
    	CM_OrganizationDraft_Page manageOrg = new CM_OrganizationDraft_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        SoftAssert soft = new SoftAssert();

        try {
            // Navigate to home page
        	driver.get(ConfigReader.getProperty("home.url")); // Use config.properties
        	wait.until(ExpectedConditions.urlContains("home"));
            Thread.sleep(2000); // Wait for page to load
            

            // Store the original window handle
            String originalWindow = driver.getWindowHandle();
            System.out.println("Original window handle: " + originalWindow);

            // Click Manage Organization link (opens new tab)
            manageOrg.clickManageOrg();

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

           
            soft.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getProperty("newtab.url")));
            
            // Perform Manage org workflow
            manageOrg.clickManageOrg();
            Thread.sleep(2000);
            
            manageOrg.clickOrg();
            Thread.sleep(2000);
            
            manageOrg.clickDraftOrg();
            Thread.sleep(2000);
        
            // setting org
 			manageOrg.clicksetOrgName();
 			Thread.sleep(2000);
 			
 			// Read from Excel
 			ExcelWSheet = ExcelWBook.getSheet("CM_OrganizationEdit");
 			String setOrgName = ExcelWSheet.getRow(3).getCell(0).toString();
 			String setOrgMail = ExcelWSheet.getRow(4).getCell(0).toString();
 			Thread.sleep(2000);

 			CM_OrganizationDraft_Page dp = new CM_OrganizationDraft_Page(driver);
 			dp.setOrgName(setOrgName);
 			Thread.sleep(3000);
            
            manageOrg.clickadminDraftOrg();
            Thread.sleep(2000);
            
            dp.setOrgMail(setOrgMail);
 			Thread.sleep(3000);

            manageOrg.clicksaveDraftOrg();
            Thread.sleep(2000);

            // Switch back to original tab if needed
            driver.switchTo().window(originalWindow);
            
            soft.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
 
    
	@AfterMethod
    public void captureFailureScreenshot(ITestResult result) throws IOException {
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