package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Page_Objects.Login_Page;
import Page_Objects.ScheduleMeetingUXValidation_Page;
import Utilities.Take_Screenshot;

public class ScheduleMeetingUXValidation {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;
	XSSFSheet AddSingleUserSheet;

	// Inside the setup method
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
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    driver.manage().window().maximize();

	    // Setup Excel file
	    File excelFile = new File("TestData\\TestDataFile.xlsx");
	    FileInputStream inputStream = new FileInputStream(excelFile);
	    ExcelWBook = new XSSFWorkbook(inputStream);
	    AddSingleUserSheet = ExcelWBook.getSheetAt(3);
	}

	@BeforeMethod
	void navigateToHomePage2() throws InterruptedException {
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
		Thread.sleep(4000); // Wait for login to complete
	}

	@Test(priority = 1) // Test case to schedule a meeting
	void Schedule_Meeting() throws InterruptedException {
		// Navigate to the Start meeting option after login
		ScheduleMeetingUXValidation_Page scheduleUX = new ScheduleMeetingUXValidation_Page(driver);

		// Click on Open Scheduler
		scheduleUX.clickScheduler();
		Thread.sleep(4000);

		// Get the scrollable card container
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement cardContainer = scheduleUX.getCardContainer();

		// Scroll down the card container incrementally
		int scrollAmount = 80; // Adjust this value for smaller steps
		int scrollDuration = 200; // Delay in milliseconds between scroll steps

		for (int i = 0; i < 5; i++) { // Adjust the number of iterations as needed
			js.executeScript("arguments[0].scrollBy(0, arguments[1]);", cardContainer, scrollAmount);
			Thread.sleep(scrollDuration);
		}

		// Click on start time
		scheduleUX.clickStartTime();
		Thread.sleep(2000);

		// Select a start time
		scheduleUX.selectStart();
		Thread.sleep(2000);

		// Click on save to schedule the meeting
		scheduleUX.selectSave();
		Thread.sleep(2000);

		// Click on cancel
		scheduleUX.selectCancel();
		Thread.sleep(2000);

		// Click on save to schedule the meeting
		scheduleUX.selectSave();
		Thread.sleep(2000);

		// Click on OK
		scheduleUX.selectOK();
		Thread.sleep(4000);

		String getToasterValue = scheduleUX.getToasterValue();

		// Verify the toaster message text
		Assert.assertEquals(getToasterValue, "Successfully scheduled Kakon Paul Avi's scheduled meeting");

	}

	@AfterMethod
	public void captureFailureScreenshot1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}

	@Test(priority = 2) // Test case to validate meeting title and meeting time
	void Schedule_Meeting_UX() throws InterruptedException {
		// Navigate to the Start meeting option after login
		ScheduleMeetingUXValidation_Page scheduleUX = new ScheduleMeetingUXValidation_Page(driver);

		// Click on meeting management
		scheduleUX.clickMeetingManagement();
		Thread.sleep(5000);

		// To validate meeting title
		String expectedTitle = "Kakon Paul Avi's scheduled meeting";
		scheduleUX.validateMeetingTitleText(expectedTitle);
		Thread.sleep(2000);

		// To validate start button text
		String expectedText = "Start";
		scheduleUX.validateStartButtonText(expectedText);
		Thread.sleep(2000);
		
		// Get and print the current URL of the new tab
	    String currentUrl = driver.getCurrentUrl();
	    System.out.println("Current URL: " + currentUrl);

	    // User redirection validation after clicking on Blog button
	    String expectedUrl = "https://meet2.synesisit.info/meeting-management";
	    Assert.assertEquals(currentUrl, expectedUrl, "URL mismatched!");

	}

	@AfterMethod
	public void captureFailureScreenshot2(ITestResult result) throws IOException {
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