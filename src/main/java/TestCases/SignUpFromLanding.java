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
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Page_Objects.SignUpFromLanding_Page;
import Page_Objects.Complete_SignUp_Page;
import Utilities.Take_Screenshot;

public class SignUpFromLanding {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet signUpSheet; // Sheet for signup data
	XSSFSheet completeSignUpSheet; // Sheet for complete sign-up data

	@BeforeClass
	void setup() throws IOException {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Setup Excel file
		File excelFile = new File("TestData\\TestDataFile.xlsx");
		FileInputStream inputStream = new FileInputStream(excelFile);

		// Load the workbook
		ExcelWBook = new XSSFWorkbook(inputStream);

		// Load specific sheets by index
		signUpSheet = ExcelWBook.getSheetAt(1); // Assume sheet at index 1 is for signup data
		completeSignUpSheet = ExcelWBook.getSheetAt(2); // Assume sheet at index 2 is for complete sign-up data
	}

	@BeforeMethod
	void navigateToSignUpPage() {
		driver.get("https://meet2.synesisit.info/");
	}

	@Test(priority = 1) //Signup test in positive case
	void SignUpTestValid() throws InterruptedException {
		// Read data from the first row of the signup sheet
		String email = signUpSheet.getRow(1).getCell(0).toString();

		System.out.println(email);

		// Perform Signup
		SignUpFromLanding_Page signUpPage = new SignUpFromLanding_Page(driver);
		signUpPage.clickSignUp();
		signUpPage.setUserName(email);
		signUpPage.clickLogin();
		Thread.sleep(2000);

		// Fetch activation link by passing the email to fetchActivationLink method
		String activationToken = printDbOutput_SignUp.fetchActivationLink(email); // Pass the email here
		String activationUrl = "https://meet2.synesisit.info/activate/" + activationToken;
		driver.get(activationUrl);
		Thread.sleep(2000);

		// Read data from the first row of the complete sign-up sheet
		String firstName = completeSignUpSheet.getRow(2).getCell(0).toString();
		String lastName = completeSignUpSheet.getRow(2).getCell(1).toString();
		String password = completeSignUpSheet.getRow(2).getCell(2).toString();

		// Complete the signup process on the activation page
		Complete_SignUp_Page completeSignUpPage = new Complete_SignUp_Page(driver);
		completeSignUpPage.setFirstName(firstName);
		completeSignUpPage.setLastName(lastName);
		completeSignUpPage.setPassword(password);

		Thread.sleep(2000);

		System.out.println("Button Text: " + completeSignUpPage.getButtonText());

		// Use JavaScriptExecutor to scroll down by 500 pixels
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500);");
		Thread.sleep(1000);

		completeSignUpPage.clickLogin();
		Thread.sleep(2000);

		// Verification to check successful signup
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// Check if the URL is the set-account page
		if (currentUrl.contains("set-account")) {
			System.out.println("User is on the set-account page, further steps may be needed.");
		}
		
		else {
			// If directly redirected to home, verify the URL
			String expectedUrl = "https://meet2.synesisit.info/home";
			Assert.assertEquals(currentUrl, expectedUrl, "URL after signup did not match the expected URL.");
		}

	    System.out.println();
	    Thread.sleep(3000);
	}

	@AfterMethod
	public void afterMethod1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
	
	@Test(priority = 2) //Signup test for duplicate email
	void SignUpTestDuplicateEmail() throws InterruptedException {
		// Read data from the first row of the signup sheet
		String email = signUpSheet.getRow(2).getCell(2).toString();

		System.out.println(email);

		// Perform Signup
		SignUpFromLanding_Page signUpPage = new SignUpFromLanding_Page(driver);
		signUpPage.clickSignUp();
		signUpPage.setUserName(email);
		signUpPage.clickLogin();
		Thread.sleep(2000);

		// Fetch activation link by passing the email to fetchActivationLink method
		String activationToken = printDbOutput_SignUp.fetchActivationLink(email); // Pass the email here
		String activationUrl = "https://meet2.synesisit.info/activate/" + activationToken;
		driver.get(activationUrl);
		Thread.sleep(2000);

		// Verification to check if signup is terminated due to duplicate email
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// Check if the URL is the set-account page
		if (currentUrl.contains("set-account")) {
			System.out.println("User is on the set-account page, further steps may be needed.");
		}
		
		else {
			// If directly redirected to home, verify the URL
			String expectedUrl = "https://meet2.synesisit.info/activate/null";
			Assert.assertEquals(currentUrl, expectedUrl, "URL after signup did not match the expected URL.");
		}
		
		System.out.println();
	    Thread.sleep(3000);
	}

	@AfterMethod
	public void afterMethod2(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@AfterClass
	void teardown() throws IOException {
		ExcelWBook.close();
		driver.quit();
	}
}