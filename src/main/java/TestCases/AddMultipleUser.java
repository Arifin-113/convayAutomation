package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Page_Objects.AddMultipleUser_Page;
import Page_Objects.AddSingleUser_Page;
import Page_Objects.Login_Page;
import Utilities.ConfigReader;
import Utilities.Take_Screenshot;

public class AddMultipleUser {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet AddSingleUserSheet;

	@BeforeClass
	void setup() throws IOException {
		// Initialize WebDriver and Excel workbook
		driver = new ChromeDriver();
		
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
	                Integer.parseInt(ConfigReader.getProperty("implicit.wait.seconds"))));
	        
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Setup Excel file
		File excelFile = new File("TestData\\TestDataFile.xlsx");
		FileInputStream inputStream = new FileInputStream(excelFile);

		// Load the workbook and sheet
		ExcelWBook = new XSSFWorkbook(inputStream);
		AddSingleUserSheet = ExcelWBook.getSheetAt(3);
	}

	@BeforeMethod
	void navigateToHomePage() throws InterruptedException {
		// Login 
    	driver.get(ConfigReader.getProperty("login.url")); // Use config.properties
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("webdriver.wait.seconds"))));
        JavascriptExecutor js = (JavascriptExecutor) driver;


		js.executeScript("document.body.style.zoom='75%'");
		Thread.sleep(3000);
		
		Login_Page lP = new Login_Page(driver);
		lP.clickContinueWithEmail();
	
		js.executeScript("document.body.style.zoom='100%'");
		Thread.sleep(3000);
		AddSingleUserSheet = ExcelWBook.getSheetAt(3); 

		AddSingleUserSheet = ExcelWBook.getSheetAt(0);

		// Reading the first row's first and second cells for username and password
		String username = AddSingleUserSheet.getRow(0).getCell(0).toString();
		String password = AddSingleUserSheet.getRow(0).getCell(1).toString();

		// Perform Login (using your Login_Page)
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(username);
		lp.setPassword(password);
		lp.clickLogin();
		Thread.sleep(4000); // Wait for login to complete
		
		// Wait for login to complete
        wait.until(ExpectedConditions.urlContains("home"));
        System.out.println("Login completed, navigated to: " + driver.getCurrentUrl());
	}

	@Test(priority = 1) // To check download Template button is working perfectly or not
	void DownloadTemplate() throws InterruptedException { // Navigate to the Add User page after login
		AddMultipleUser_Page addMultipleUserPage = new AddMultipleUser_Page(driver);

		// Open the Admin dropdown
		addMultipleUserPage.clickAdminDropdown();
		Thread.sleep(2000);

		// Click on "Manage Users"
		addMultipleUserPage.clickManageUsers();
		Thread.sleep(2000);

		// Click on "Add Multiple User"
		addMultipleUserPage.clickAddMultipleUser1();
		Thread.sleep(2000);

		// Click on Download button
		addMultipleUserPage.clickDownload();
		Thread.sleep(2000);
	}

	@AfterMethod
	public void captureFailureScreenshot1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 2) // To check Upload template and add multiple user functionalities
	void UploadTemplate() throws InterruptedException {
		AddMultipleUser_Page addMultipleUserPage = new AddMultipleUser_Page(driver);

		// Open the Admin dropdown
		addMultipleUserPage.clickAdminDropdown();
		Thread.sleep(2000);

		// Click on "Manage Users"
		addMultipleUserPage.clickManageUsers();
		Thread.sleep(2000);

		// Click on "Add Multiple User"
		addMultipleUserPage.clickAddMultipleUser1();
		Thread.sleep(2000);

		// Click on Upload button
		addMultipleUserPage.clickUpload();
		Thread.sleep(2000);

		// Upload the file
		String filePath = "C:\\Users\\USER\\Downloads\\b7fadd37-0fe3-42f2-9bf8-f0793f8f8b18.csv";
		addMultipleUserPage.uploadFile(filePath); // This sends the file path directly to the input field
		Thread.sleep(4000);

		// Click on Confirm button
		addMultipleUserPage.clickConfirm();
		Thread.sleep(2000);

		// Use JavaScriptExecutor to scroll down by 500 pixels
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500);");
		Thread.sleep(1000);

		// Click on Send Invitation button
		addMultipleUserPage.clickSendInvitation();
		Thread.sleep(4000);

		// Verify if user is successfully added by checking redirection or a success message
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = "https://meet2.synesisit.info/add-user"; // Expected URL after adding a user

		Assert.assertEquals(currentUrl, expectedUrl, "User not added successfully, URL did not match expected.");

	}

	@AfterMethod
	public void captureFailureScreenshot2(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 3) // To check failed message after giving an empty file
	void FailedMessage() throws InterruptedException {
		// Navigate to the Add User page after login
		AddMultipleUser_Page addMultipleUserPage = new AddMultipleUser_Page(driver);

		// Open the Admin dropdown
		addMultipleUserPage.clickAdminDropdown();
		Thread.sleep(2000);

		// Click on "Manage Users"
		addMultipleUserPage.clickManageUsers();
		Thread.sleep(2000);

		// Click on "Add Multiple User"
		addMultipleUserPage.clickAddMultipleUser1();
		Thread.sleep(2000);

		// Click on Upload button
		addMultipleUserPage.clickUpload();
		Thread.sleep(2000);

		// Upload the file
		String filePath = "C:\\Users\\USER\\Downloads\\b7fadd37-0fe3-42f2-9bf8-f0793f8f8b18 (11).csv";
		addMultipleUserPage.uploadFile(filePath); // This sends the file path directly to the input field
		Thread.sleep(4000);

		// Get the failed message and verify it
		AddMultipleUser_Page AddMultipleUser_Page = new AddMultipleUser_Page(driver);
		String actualErrorMessage = AddMultipleUser_Page.getFailedMessage();
		String expectedErrorMessage = "File upload failed";

		Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Success message did not match.");
	}

	@AfterMethod
	public void captureFailureScreenshot3(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 4) // To check upload Template functionality is working perfectly or not
	void SuccessMessage() throws InterruptedException {
		// Navigate to the Add User page after login
		AddMultipleUser_Page addMultipleUserPage = new AddMultipleUser_Page(driver);

		// Open the Admin dropdown
		addMultipleUserPage.clickAdminDropdown();
		Thread.sleep(2000);

		// Click on "Manage Users"
		addMultipleUserPage.clickManageUsers();
		Thread.sleep(2000);

		// Click on "Add Multiple User"
		addMultipleUserPage.clickAddMultipleUser1();
		Thread.sleep(2000);

		// Click on Upload button
		addMultipleUserPage.clickUpload();
		Thread.sleep(2000);

		// Upload the file
		String filePath = "C:\\Users\\USER\\Downloads\\b7fadd37-0fe3-42f2-9bf8-f0793f8f8b18.csv";
		addMultipleUserPage.uploadFile(filePath); // This sends the file path directly to the input field
		Thread.sleep(4000);

		// Click on Confirm button
		addMultipleUserPage.clickConfirm();
		Thread.sleep(2000);

		// Get the failed message and verify it
		AddMultipleUser_Page AddMultipleUser_Page = new AddMultipleUser_Page(driver);
		String actualErrorMessage = AddMultipleUser_Page.getSuccessMessage();
		String expectedErrorMessage = "Successfully Uploaded";

		Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Success message did not match.");
	}

	@AfterMethod
	public void captureFailureScreenshot4(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@AfterClass
	void teardown() throws IOException {
		ExcelWBook.close();
		driver.close();
	}

}
