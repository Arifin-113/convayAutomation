package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Page_Objects.Login_Page;
import Page_Objects.InstantMeetingUX_Page;
import Utilities.Take_Screenshot;

public class InstantMeetingUX {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;
	XSSFSheet AddSingleUserSheet;

	// Inside the setup method

	@BeforeClass
	void setup() throws IOException {
		// Initialize WebDriver and Excel workbook
		driver = new ChromeDriver();
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
	void navigateToHomePage2() throws InterruptedException {
		// Login before add users
		driver.get("https://meet2.synesisit.info/sign-in");

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
	}

	@Test(priority = 1) // Test case to validate UX of instant meeting
	void instantMeetingUX() throws InterruptedException {
		// Navigate to the Start meeting option after login
		InstantMeetingUX_Page instant = new InstantMeetingUX_Page(driver);

		// Capture the meeting Host name of the upcoming meeting
		String initialValue = instant.getID();
		Thread.sleep(2000);

		// Assert the upcoming meeting ID
		String afterGetID = instant.getID();
		Assert.assertEquals(afterGetID, initialValue, "6477 1284 0966");

		// Capture the instant meeting button text
		String initialValue2 = instant.getInstantButtonText();

		// Assert the instant meeting button text
		String afterGetInstantButtonText = instant.getInstantButtonText();
		Assert.assertEquals(afterGetInstantButtonText, initialValue2, "Start Now");

		// Click on "Start Now" button
		instant.clickInstantMeeting();
		Thread.sleep(3000);

		// Capture the "Start" button text
		String initialValue3 = instant.getStartButtonText();

		// Assert the "Start" button text
		String afterGetStartButtonText = instant.getStartButtonText();
		Assert.assertEquals(afterGetStartButtonText, initialValue3, "Start");

		// Click on "Cross" button
		instant.clickCross();
		Thread.sleep(2000);

		// Get and print the current URL of the new tab
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// User redirection validation after clicking on cross button
		String expectedUrl = "https://meet2.synesisit.info/home";
		Assert.assertEquals(currentUrl, expectedUrl, "URL mismatched!");
	}

	@AfterMethod
	public void captureFailureScreenshot1(ITestResult result) throws IOException {
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