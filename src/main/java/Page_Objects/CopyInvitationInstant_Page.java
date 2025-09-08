package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CopyInvitationInstant_Page {

	WebDriver driver;
	WebDriverWait wait;

	// Define locators
	By copy = By.xpath("//*[name()='g' and contains(@data-name,'Group 4930')]//*[name()='rect' and contains(@data-name,'Rectangle ')]");
//	By btn_acceptCookies = By.xpath("//button[@data-cky-tag='accept-button']");
	By toaster_Message = By.xpath("//div[@class='cnv-toast-body']");
	

	// Constructor
	public CopyInvitationInstant_Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click Copy Invitation
	public void clickCopy() {
		driver.findElement(copy).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Method to click on Accept Cookies
	public void clickAccept() {
//		driver.findElement(btn_acceptCookies).click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	 public String getToasterValue() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toaster_Message));
	        return toastElement.getText();
	}

}
