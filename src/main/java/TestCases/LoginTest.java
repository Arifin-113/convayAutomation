package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import com.sun.org.apache.bcel.internal.classfile.Utility;

import Page_Objects.Login_Page;
import Utilities.Take_Screenshot;

public class LoginTest {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;

	@BeforeClass
	void setup() throws IOException {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Setup Excel file
		File excelFile = new File("TestData\\TestDataFile.xlsx");
		FileInputStream inputStream = new FileInputStream(excelFile);

		// Load the workbook and sheet
		ExcelWBook = new XSSFWorkbook(inputStream);
		ExcelWSheet = ExcelWBook.getSheetAt(0);
	}

	@BeforeMethod
	void navigateToSignInPage() {
		driver.get("https://convay.com/sign-in");
	}

	@Test(priority = 1) // Test case for Valid User and Valid Password
	void LoginValCred() throws InterruptedException {
		// driver.get("https://convay.com/sign-in");

		// Reading the first row's first and second cells for username and password
		String username = ExcelWSheet.getRow(0).getCell(0).toString();
		String password = ExcelWSheet.getRow(0).getCell(1).toString();

		// Perform Login
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(username);
		lp.setPassword(password);
		lp.clickLogin();
		Thread.sleep(2000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// User redirection validation after successful sign in
		String expectedUrl = "https://convay.com/home";

		Assert.assertEquals(currentUrl, expectedUrl, "URL after login did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 2) // Test case for invalid Username and Valid Password
	void LoginInvUname() throws InterruptedException {

		// Reading the 2nd row's first and second cells for username and password
		String username_Inv = ExcelWSheet.getRow(1).getCell(0).toString();
		String password_Val = ExcelWSheet.getRow(1).getCell(1).toString();

		// Perform Login
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(username_Inv);
		lp.setPassword(password_Val);
		lp.clickLogin();
		Thread.sleep(2000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// To check page validation
		String expectedUrl = "https://convay.com/sign-in";

		Assert.assertEquals(currentUrl, expectedUrl, "URL did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod2(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 3) // Test case for valid Username and invalid Password
	void LoginInvPass() throws InterruptedException {

		// Reading the 3rd row's first and second cells for username and password
		String username_Val = ExcelWSheet.getRow(2).getCell(0).toString();
		String password_Inv = ExcelWSheet.getRow(2).getCell(1).toString();

		// Perform Login
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(username_Val);
		lp.setPassword(password_Inv);
		lp.clickLogin();
		Thread.sleep(2000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// To check page validation
		String expectedUrl = "https://convay.com/sign-in";

		Assert.assertEquals(currentUrl, expectedUrl, "URL did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod3(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 4) // Test case for invalid Username and invalid Password
	void LoginInvUnamePass() throws InterruptedException {

		// Reading the 4th row's first and second cells for username and password
		String username_Inv = ExcelWSheet.getRow(3).getCell(0).toString();
		String password_Inv = ExcelWSheet.getRow(3).getCell(1).toString();

		// Perform Login
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(username_Inv);
		lp.setPassword(password_Inv);
		lp.clickLogin();
		Thread.sleep(2000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// To check page validation
		String expectedUrl = "https://convay.com/sign-in";

		Assert.assertEquals(currentUrl, expectedUrl, "URL did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod4(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 5) // Test case for blank credential
	void LoginWithoutCred() throws InterruptedException {

		// Reading the 5th row's first and second cells for username and password
		String No_username = ExcelWSheet.getRow(4).getCell(0).toString();
		String No_password = ExcelWSheet.getRow(4).getCell(1).toString();

		// Perform Login
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(No_username);
		lp.setPassword(No_password);
		lp.clickLogin();
		Thread.sleep(2000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// To check page validation
		String expectedUrl = "https://convay.com/sign-in";

		Assert.assertEquals(currentUrl, expectedUrl, "URL did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod5(ITestResult result) throws IOException {
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