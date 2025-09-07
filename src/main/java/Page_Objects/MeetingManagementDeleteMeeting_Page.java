package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MeetingManagementDeleteMeeting_Page {

	WebDriver driver;
	WebDriverWait wait;

	// Define locators
	By sceduler_loc = By.xpath("//div[contains(@class,'cnv-open-box')]");
	By startTime_loc = By.xpath("//div[@class='cnv-form-group-col-2-select']//div[1]//div[1]//div[1]//div[1]//*[name()='svg']");
	By selectStartTime_loc = By.xpath("//div[@class='cnv-form-group-col-2-select']//div[1]//div[1]//div[1]//div[1]//*[name()='svg']");
	By save_loc = By.xpath("//button[@id='save-schedule']");
	By ok_loc = By.xpath("//button[normalize-space()='Ok']");
	By toaster_Message = By.xpath("//div[@class='cnv-toast-body']");
	By meetingManagement_loc = By.xpath("//span[normalize-space()='Meeting Management']");
	By selectMeeting_loc = By.xpath("//div[@class='cnv-flex-one']");
	By deleteMeeting_loc = By.xpath("//div[4]//*[name()='svg']//*[name()='g' and contains(@data-name,'Group 1106')]//*[name()='rect' and contains(@data-name,'Rectangle ')]");
	By deleteCancel_loc = By.xpath("//button[normalize-space()='Cancel']");
	By deleteConfirm_loc = By.xpath("//button[normalize-space()='Delete']");
	
	// Constructor
	public MeetingManagementDeleteMeeting_Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click Open Scheduler
	public void clickScheduler() {
		driver.findElement(sceduler_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public WebElement getCardContainer() {
		// Locate the scrollable container element within the card
		return driver.findElement(By.xpath("//div[@class='cnv-left-sidebar']//li[3]//a[1]"));
	}

	// Method to click on start time to schedule a meeting
	public void clickStartTime() {
		driver.findElement(startTime_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to select a start time
	public void selectStart() {
		driver.findElement(selectStartTime_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to save the schedule meeting
	public void selectSave() {
		driver.findElement(save_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}


	// Method to click on OK button
	public void selectOK() {
		driver.findElement(ok_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click on Meeting Management
	public void clickMeetingManagement() {
		driver.findElement(meetingManagement_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to select a scheduled meeting to edit
	public void selectMeeting() {
		driver.findElement(selectMeeting_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click on Delete button
	public void clickDelete() {
		driver.findElement(deleteMeeting_loc).click();
	}

	// Method to click on Cancel button
	public void clickCancel() {
		driver.findElement(deleteCancel_loc).click();
	}

	// Method to click on OK button
	public void clickOK() {
		driver.findElement(deleteConfirm_loc).click();
	}

	public String getToasterValue() {
		// Wait for the toaster message to be visible and return its text
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement toasterMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toaster_Message));
		return toasterMessageElement.getText();
	}

}