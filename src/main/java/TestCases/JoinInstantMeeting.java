package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import com.sun.org.apache.bcel.internal.classfile.Utility;

import Page_Objects.JoinInstantMeeting_Page;
import Page_Objects.Login_Page;
import Utilities.Take_Screenshot;

public class JoinInstantMeeting {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;
	XSSFSheet AddSingleUserSheet;

	@BeforeClass
	void setup() throws IOException {
		// Set Chrome preferences to allow microphone access globally
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> contentSettings = new HashMap<>();
		HashMap<String, Object> profile = new HashMap<>();
		HashMap<String, Object> prefs = new HashMap<>();

		// Set microphone permission to allow
		contentSettings.put("media_stream_mic", 1); // 1 = allow; 2 = block
		profile.put("managed_default_content_settings", contentSettings);
		prefs.put("profile", profile);
		options.setExperimentalOption("prefs", prefs);

		// Additional flag to bypass microphone permission prompt
		options.addArguments("--use-fake-ui-for-media-stream");

		// Initialize WebDriver with ChromeOptions
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Setup Excel file
		File excelFile = new File("TestData\\TestDataFile.xlsx");
		FileInputStream inputStream = new FileInputStream(excelFile);

		// Load the workbook and sheet
		ExcelWBook = new XSSFWorkbook(inputStream);
		ExcelWSheet = ExcelWBook.getSheet("Join_InstantMeeting");
	}

	@BeforeMethod
	void navigateToSignInPage() {
		// Login before add users
		driver.get("https://meet2.synesisit.info/sign-in");

		AddSingleUserSheet = ExcelWBook.getSheetAt(18);

		// Reading the first row's first and second cells for username and password
		String username = AddSingleUserSheet.getRow(0).getCell(0).toString();
		String password = AddSingleUserSheet.getRow(0).getCell(1).toString();

		// Perform Login (using your Login_Page)
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(username);
		lp.setPassword(password);
		lp.clickLogin();

	}

	@Test(priority = 1) // Test case to join a meeting with meeting ID
	void JoinWithMeetingID() throws InterruptedException {

		JoinInstantMeeting_Page JoinInstant = new JoinInstantMeeting_Page(driver);

		// Reading the first row's first cell for meeting ID
		String MeetingID = ExcelWSheet.getRow(0).getCell(0).toString();

		// Perform Actions
		// Click on Join button from home page
		JoinInstant.clickJoinMeeting();
		Thread.sleep(2000);

		// Input meeting ID
		JoinInstant.setMeetingID(MeetingID);
		Thread.sleep(2000);

		// Click on continue
		JoinInstant.clickContinue();
		Thread.sleep(10000);

		// Store the current window handle
		String originalWindow = driver.getWindowHandle();

		// Wait for the new tab to open and switch to it
		for (String windowHandle : driver.getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}

		// Close the new tab and switch back to the original window
		driver.close();
		Thread.sleep(2000);
		driver.switchTo().window(originalWindow);
	}

	@AfterMethod
	public void Aftermethod1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 2) // Test case to join a meeting with meeting Link
	void JoinWithMeetingLink() throws InterruptedException {

		JoinInstantMeeting_Page JoinInstant = new JoinInstantMeeting_Page(driver);

		// Reading the second row's first cell for meeting link
		String MeetingLink = ExcelWSheet.getRow(1).getCell(0).toString();

		// Perform Actions
		// Click join button from home page
		JoinInstant.clickJoinMeeting();
		Thread.sleep(2000);

		// Input meeting link
		JoinInstant.setMeetingLink(MeetingLink);
		System.out.println("Extracted Meeting Link: [" + MeetingLink + "]");
		Thread.sleep(2000);

		// Click on continue
		JoinInstant.clickContinue();
		Thread.sleep(10000);

		/*// Store the current window handle
		String originalWindow = driver.getWindowHandle();

		// Wait for the new tab to open and switch to it
		for (String windowHandle : driver.getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}

		// Close the new tab and switch back to the original window
		driver.close();
		Thread.sleep(2000);
		driver.switchTo().window(originalWindow);*/
	}


	@AfterMethod
	public void Aftermethod2(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 3) // Test case to join a meeting without meeting ID & Link from home page
	void JoinWithoutIDLink() throws InterruptedException {

		JoinInstantMeeting_Page JoinInstant = new JoinInstantMeeting_Page(driver);

		// Reading the third row's first cell for meeting link
		String Blank = ExcelWSheet.getRow(2).getCell(0).toString();

		// Perform Actions
		// Click join button from home page
		JoinInstant.clickJoinMeeting();
		Thread.sleep(2000);

		// Input meeting link
		JoinInstant.setMeetingLink(Blank);
		Thread.sleep(2000);

		// Click on continue
		JoinInstant.clickContinue();
		Thread.sleep(10000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// User redirection validation after clicking on continue
		String expectedUrl = "https://meet2.synesisit.info/home";

		Assert.assertEquals(currentUrl, expectedUrl, "URL after login did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod3(ITestResult result) throws IOException {
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