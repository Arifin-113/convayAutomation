package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
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

import Page_Objects.EditUserInfo_Page;
import Page_Objects.ForgetPassword_Page;
import Utilities.Take_Screenshot;

public class ForgetPassword {

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

		// Load the workbook and sheet by name instead of index for better clarity
		ExcelWBook = new XSSFWorkbook(inputStream);
		ExcelWSheet = ExcelWBook.getSheetAt(8);
	}

	@BeforeMethod
	void navigateToForgetPasswordPage() {
		driver.get("https://meet2.synesisit.info/sign-in");
	}
/*
	@Test(priority = 1) // Test case for Forget Password functionality
	void forgetPasswordTest1() throws InterruptedException {
		ForgetPassword_Page forgetPasswordPage = new ForgetPassword_Page(driver);

		forgetPasswordPage.clickForgetPassword();
		Thread.sleep(2000);

		forgetPasswordPage.clickSend();
		Thread.sleep(2000);

		String getRequiredMessage = forgetPasswordPage.getRequiredMessage();

		// Verify the required message text
		Assert.assertEquals(getRequiredMessage, "Required");
	}

	@AfterMethod
	public void forgetPass1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
*/
	@Test(priority = 2) // To check the validation with an unregistered email
	void forgetPasswordTest2() throws InterruptedException {
		ForgetPassword_Page forgetPasswordPage = new ForgetPassword_Page(driver);

		Thread.sleep(2000);
		forgetPasswordPage.clickForgetPassword();
		Thread.sleep(2000);

		// Load the new sheet for user data
		ExcelWSheet = ExcelWBook.getSheetAt(8);

		// Reading new user data from the Excel sheet
		String mail = ExcelWSheet.getRow(0).getCell(0).toString();

		// Input email
		forgetPasswordPage.setEmail(mail);
		Thread.sleep(1000);

		forgetPasswordPage.clickSend();
		Thread.sleep(2000);

		String getFailedMessage = forgetPasswordPage.getRequiredMessage();

		// Verify the message text
		Assert.assertEquals(getFailedMessage, "Invalid email");
	}

	@AfterMethod
	public void forgetPass2(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
/*
	@Test(priority = 3) // To check email has been sent perfectly after requesting forget password
	void forgetPasswordTest3() throws InterruptedException {
		ForgetPassword_Page forgetPasswordPage = new ForgetPassword_Page(driver);

		Thread.sleep(2000);
		forgetPasswordPage.clickForgetPassword();
		Thread.sleep(2000);

		// Load the new sheet for user data
		ExcelWSheet = ExcelWBook.getSheetAt(8);

		// Reading new user data from the Excel sheet
		String verify = ExcelWSheet.getRow(1).getCell(0).toString();

		// Input email
		forgetPasswordPage.setEmail(verify);
		Thread.sleep(2000);

		forgetPasswordPage.clickSend();
		Thread.sleep(2000);

		String getVerifyMessage = forgetPasswordPage.getVerifyEmail();

		// Verify the message text
		Assert.assertEquals(getVerifyMessage, "Verify Email");
	}

	@AfterMethod
	public void forgetPass3(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 4) // To check resend email
	void forgetPasswordTest4() throws InterruptedException {
		ForgetPassword_Page forgetPasswordPage = new ForgetPassword_Page(driver);

		Thread.sleep(2000);
		forgetPasswordPage.clickForgetPassword();
		Thread.sleep(2000);

		// Load the new sheet for user data
		ExcelWSheet = ExcelWBook.getSheetAt(8);

		// Reading new user data from the Excel sheet
		String verify = ExcelWSheet.getRow(1).getCell(0).toString();

		// Input email
		forgetPasswordPage.setEmail(verify);
		Thread.sleep(2000);

		forgetPasswordPage.clickSend();
		Thread.sleep(32000);

		forgetPasswordPage.clickResend();
		Thread.sleep(2000);
	}

	@AfterMethod
	public void forgetPass4(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
*/
	@Test(priority = 5) // To check forget password
	void forgetPasswordTest5() throws InterruptedException {
		ForgetPassword_Page forgetPasswordPage = new ForgetPassword_Page(driver);

		String id = ExcelWSheet.getRow(1).getCell(0).toString();

		System.out.println(id);

		Thread.sleep(2000);
		forgetPasswordPage.clickForgetPassword();
		Thread.sleep(2000);

		// Load the new sheet for user data
		ExcelWSheet = ExcelWBook.getSheetAt(8);

		// Reading new user data from the Excel sheet
		String verify = ExcelWSheet.getRow(1).getCell(0).toString();
		String newPass = ExcelWSheet.getRow(1).getCell(1).toString();
		String confirmPass = ExcelWSheet.getRow(1).getCell(2).toString();

		// Input email
		forgetPasswordPage.setEmail(verify);
		Thread.sleep(2000);

		forgetPasswordPage.clickSend();
		Thread.sleep(2000);

		// Fetch activation link by passing the email to fetchActivationLink method
		String activationToken = printDbOutput_ForgetPassword.fetchActivationLink(id); // Pass the email here
		String activationUrl = "https://meet2.synesisit.info/activate/" + activationToken;
		driver.get(activationUrl);
		Thread.sleep(2000);
		
		//Set new password
		forgetPasswordPage.setEmail(newPass);
		Thread.sleep(2000);
		
		//Confirm Password
		forgetPasswordPage.setEmail(confirmPass);
		Thread.sleep(2000);

	}

	@AfterMethod
	public void forgetPass5(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@AfterClass
	void teardown() throws IOException {
		ExcelWBook.close();
		driver.quit(); // Use quit() to close the entire browser session
	}
}
