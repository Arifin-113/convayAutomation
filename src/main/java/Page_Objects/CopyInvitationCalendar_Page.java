package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CopyInvitationCalendar_Page {

	WebDriver driver;
	WebDriverWait wait;

	// Define locators
	By sceduler_loc = By.xpath("//div[contains(@class,'cnv-open-box')]");
	By save_loc = By.xpath("//button[@id='save-schedule']");
	
	
	
	
	By ok_loc = By.xpath("//button[normalize-space()='Ok']");
	By cancel_loc = By.xpath("//button[@id='cancel-schedule']");
	By cancelSc_loc = By.xpath("//button[@class='cnv-btn cnv-btn-save']");
	By meeting_loc = By.xpath("//div[contains(@class,'cnv-calendar-event-meta')]");
	
//	By meeting_loc = By.xpath("//div class="cnv-alert-content']");
	
	By copy_loc = By.xpath("//div[@class='cnv-calendar-event-actions undefined']//*[name()='svg']");
	By toaster_Message = By.xpath("//div[@class='cnv-toast-body']");

	// Constructor
	public CopyInvitationCalendar_Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click Open Sceduler
	public void clickScheduler() {
		driver.findElement(sceduler_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/*public WebElement getCardContainer() {
		// Locate the scrollable container element within the card
		return driver.findElement(By.xpath("//body/div[@id='root']/div[@class='cnv-wrapper cnv-theme-light']/div[@class='cnv-content-area']/div[@class='cnv-content']/div[@id='portalForModal']/div[@class='cnv-container-fluid Scheduler_main__F23x0']/div[@class='Scheduler_scheduleCreate__2kizM']/div[@class='cnv-container']/div[@class='cnv-card schedule-create-custom']/div[@class='cnv-card-body']/div[@id='taber1']/div[@id='event-details']/div/div[1]"));
	}*/

	// Method to click on Save button
	public void clickSave() {
		driver.findElement(save_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click on OK button
	public void clickOK() {
		driver.findElement(ok_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click on cancel button
	public void clickCancel() {
		driver.findElement(cancel_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	// Method to click on cancelSc button 
		public void clickCancelSc() {
			driver.findElement(cancelSc_loc).click();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		}

	// Method to click on Meeting
	public void clickMeeting() {
		driver.findElement(meeting_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click on Copy
	public void clickCopy() {
		driver.findElement(copy_loc).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public String getToasterValue() {
		// Wait for the toaster message to be visible and return its text
		WebElement toasterMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toaster_Message));
		return toasterMessageElement.getText();
	}

}
