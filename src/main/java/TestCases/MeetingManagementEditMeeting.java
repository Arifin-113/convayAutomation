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
import Page_Objects.MeetingManagementEditMeeting_Page;
import Utilities.Take_Screenshot;

public class MeetingManagementEditMeeting {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;
	XSSFSheet AddSingleUserSheet;

	// Inside the setup method
	@BeforeClass
    void setup() throws IOException {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> contentSettings = new HashMap<>();
        HashMap<String, Object> profile = new HashMap<>();
        HashMap<String, Object> prefs = new HashMap<>();
        contentSettings.put("media_stream_mic", 1);
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);
        ExcelWBook = new XSSFWorkbook(inputStream);
        AddSingleUserSheet = ExcelWBook.getSheetAt(3);
    }

    @BeforeMethod
    void navigateToHomePage2() throws InterruptedException {
        driver.get("https://meet2.synesisit.info/sign-in");
        AddSingleUserSheet = ExcelWBook.getSheetAt(18);
        String username = AddSingleUserSheet.getRow(0).getCell(0).toString();
        String password = AddSingleUserSheet.getRow(0).getCell(1).toString();
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();
        Thread.sleep(4000);
    }


    
	@Test(priority = 1) // Test case to schedule a meeting
	void Schedule_Meeting() throws InterruptedException {
		// Navigate to the Start meeting option after login
		MeetingManagementEditMeeting_Page editMeeting = new MeetingManagementEditMeeting_Page(driver);

		// Click on Open Scheduler
        editMeeting.clickScheduler();
        Thread.sleep(2000);

        // Click save (default time is used)
        editMeeting.selectSave();
        Thread.sleep(2000);

        // Click OK
        editMeeting.selectOK();
        Thread.sleep(4000);
        // Verify toaster message
//        String toasterValue = editMeeting.getToasterValue();
//        System.out.println("Toaster: " + toasterValue); // For debugging
//        Assert.assertEquals(toasterValue, "Successfully updated Kakon Paul Avi's scheduled meeting");
    }

    @AfterMethod
    public void captureFailureScreenshot(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
        }
    }
	
	@Test(priority = 2) // Test case to edit a scheduled meeting
	void Schedule_Meeting_Edit() throws InterruptedException {
		// Navigate to the Start meeting option after login
		MeetingManagementEditMeeting_Page editMeeting = new MeetingManagementEditMeeting_Page(driver);

		// Click on meeting management
		editMeeting.clickMeetingManagement();
		Thread.sleep(5000);

		// Select a meeting to edit
		editMeeting.selectMeeting();
		Thread.sleep(4000);

		// Click on edit button
		editMeeting.clickEdit();
		Thread.sleep(4000);

		// Get the scrollable card container
		JavascriptExecutor js = (JavascriptExecutor) driver;
//		WebElement cardContainer = editMeeting.getCardContainer();

		// Scroll down the card container incrementally
		int scrollAmount = 80; // Adjust this value for smaller steps
		int scrollDuration = 200; // Delay in milliseconds between scroll steps

		for (int i = 0; i <= 5; i++) { // Adjust the number of iterations as needed
//			js.executeScript("arguments[0].scrollBy(0, arguments[1]);", cardContainer, scrollAmount);
			Thread.sleep(scrollDuration);
		}

		// Click on start time
		editMeeting.clickStartTime();
		Thread.sleep(2000);

		// Select a start time
//		editMeeting.selectStart();
		Thread.sleep(2000);

		// Click on save to schedule the meeting
		editMeeting.selectSave();
		Thread.sleep(2000);

		// Click on cancel
		editMeeting.selectCancel();
		Thread.sleep(2000);

		// Click on save to schedule the meeting
		editMeeting.selectSave();
		Thread.sleep(2000);

		// Click on OK
		editMeeting.selectOK();
		Thread.sleep(4000);

//		String getToasterValue = editMeeting.getToasterValue();

		// Verify the toaster message text
//		Assert.assertEquals(getToasterValue, "Successfully updated Kakon Paul Avi's scheduled meeting");
		


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