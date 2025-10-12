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
import Page_Objects.CM_ManagePlansEditPlan_Page;
import Page_Objects.CM_ManagePlansSearch_Page;
import Utilities.ConfigReader;
import Utilities.Take_Screenshot;

public class CM_ManagePlansEditPlan {

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
        // Login 
    	driver.get(ConfigReader.getProperty("login.url")); // Use config.properties
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("webdriver.wait.seconds"))));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Login_Page lP = new Login_Page(driver);
        
		
		js.executeScript("document.body.style.zoom='75%'");
		Thread.sleep(3000);
		
		lP.clickContinueWithEmail();
		

		js.executeScript("document.body.style.zoom='100%'");
		Thread.sleep(3000);
		ExcelWSheet = ExcelWBook.getSheetAt(0); 

        // Read username and password from Excel
        String username = ExcelWSheet.getRow(5).getCell(0).toString();
        String password = ExcelWSheet.getRow(5).getCell(1).toString();

        // Perform login
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();

        // Wait for login to complete
        wait.until(ExpectedConditions.urlContains("home"));
        System.out.println("Login completed, navigated to: " + driver.getCurrentUrl());

    }
  
    @Test(priority = 1)
    void CM_ManagePlans_EditPlanBY_ClickingPlan() throws InterruptedException {
    	CM_ManagePlansEditPlan_Page managePlans = new CM_ManagePlansEditPlan_Page(driver);
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
            managePlans.clickManageOrg();

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
            
            // Perform Manage Plans workflow
            managePlans.clickManagePlans();
            Thread.sleep(2000);
            
            
            // Search for the created plan 
            managePlans.clickSearchPlans();
            Thread.sleep(2000);
  
            // Read plan name from Excel
            ExcelWSheet = ExcelWBook.getSheetAt(22); 
            String planName = ExcelWSheet.getRow(0).getCell(0).toString();
            
            Thread.sleep(2000);
            
            CM_ManagePlansSearch_Page cp = new CM_ManagePlansSearch_Page(driver);
            cp.setPlanName(planName);
            Thread.sleep(3000);
            
            // Click Plan to open edit form
            managePlans.clickPlan();
            Thread.sleep(2000);
           

            // Edit Button Locator
            managePlans.editButton2();
            Thread.sleep(2000);
            // typeChooseOrg
            managePlans.typeChooseOrg();
			Thread.sleep(2000);

			// Search for the created plan
			managePlans.clickNamePlans();
			Thread.sleep(2000);

			managePlans.clearPlanName();

			// Read plan name from Excel for Updating
			ExcelWSheet = ExcelWBook.getSheet("CM_ManageSearchPlan");
			String planName2 = ExcelWSheet.getRow(1).getCell(0).toString();
			Thread.sleep(2000);

			CM_ManagePlansEditPlan_Page ep = new CM_ManagePlansEditPlan_Page(driver);
			ep.setPlanName2(planName2);
			Thread.sleep(3000);
			
			managePlans.clickUpdateButton();
			Thread.sleep(2000);
			
			managePlans.clickOkAfterUpdate();
			Thread.sleep(2000);

            // Switch back to original tab if needed
            driver.switchTo().window(originalWindow);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
    
    @Test(priority = 2)
    void CM_ManagePlans_EditPlanBy_ClickingActionButton() throws InterruptedException {
    	CM_ManagePlansEditPlan_Page managePlans = new CM_ManagePlansEditPlan_Page(driver);
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
            managePlans.clickManageOrg();

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
            
            // Perform Manage Plans workflow
            managePlans.clickManagePlans();
            Thread.sleep(2000);
            
            
            // Search for the created plan 
            managePlans.clickSearchPlans();
            Thread.sleep(2000);
  
            // Read plan name from Excel
            ExcelWSheet = ExcelWBook.getSheetAt(22); 
            String planName = ExcelWSheet.getRow(1).getCell(0).toString();
            
            Thread.sleep(2000);
            
            CM_ManagePlansSearch_Page cp = new CM_ManagePlansSearch_Page(driver);
            cp.setPlanName(planName);
            Thread.sleep(3000);
            
            // Click Plan to open edit form
            managePlans.clickPlan();
            Thread.sleep(2000);
           

            // Edit Button Locator
            managePlans.editButton2();
            Thread.sleep(2000);
            // typeChooseOrg
            managePlans.typeChooseOrg();
			Thread.sleep(2000);

			// Search for the created plan
			managePlans.clickNamePlans();
			Thread.sleep(2000);

			managePlans.clearPlanName();

			// Read plan name from Excel for Updating
			ExcelWSheet = ExcelWBook.getSheet("CM_ManageSearchPlan");
			String planName2 = ExcelWSheet.getRow(0).getCell(0).toString();
			Thread.sleep(2000);

			CM_ManagePlansEditPlan_Page ep = new CM_ManagePlansEditPlan_Page(driver);
			ep.setPlanName2(planName2);
			Thread.sleep(3000);
			
			managePlans.clickUpdateButton();
			Thread.sleep(2000);
			
			managePlans.clickOkAfterUpdate();
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
