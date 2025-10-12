package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class CM_OrganizationCreateNew_Page {

	WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By org = By.xpath("//span[normalize-space()='Organizations']"); 
    By create_orgBtn = By.xpath("//a[@class='btnPrimary']"); 
    By setorgName_text = By.xpath("//input[@id='name']");
    By setorgWebsite = By.xpath("//input[@id='website']");
    By setorgCountry = By.xpath("//*[@id=\"organizationForm\"]/div/div[2]/div/div[1]/div[2]/select/option[19]");
    
    
  
    By superAdmin_org = By.xpath("//div[contains(text(),'Super Admin')]");
    By setAdminFirstName = By.xpath("//input[@placeholder='Fast name']");
    By setAdminLastName = By.xpath("//input[@placeholder='Last name']");
    By setAdminContactNumber = By.xpath("//input[@value='+880']");
    By setAdmingMail_text = By.xpath("//input[contains(@placeholder,'Enter Email Address')]");
    By saveCreate_orgBtn = By.xpath("//button[normalize-space()='Create']");

    // Constructor
    public CM_OrganizationCreateNew_Page (WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }
    
    // Method to click Manage organization
    public void clickOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(org)).click();
    }

    // Method to create new organization
    public void clickCreateOrgBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(create_orgBtn)).click();
    }

    // Method to click for org
  	public void clicksetOrgName() {
  		wait.until(ExpectedConditions.elementToBeClickable(setorgName_text)).click();
  	}
  	
  	//Method to set name
	public void setOrgName(String pn) {
		driver.findElement(setorgName_text).sendKeys(pn);
	}
	
	
    
	// Method to click super admin organization
    public void clickSuperAdminOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(superAdmin_org)).click();
    }
    
    
 // Method to click for org
   	public void clicksetAdminFirstName() {
   		wait.until(ExpectedConditions.elementToBeClickable(setAdminFirstName)).click();
   	}
   	
   	//Method to set name
 	public void setAdminFirstName(String pn) {
 		driver.findElement(setAdminFirstName).sendKeys(pn);
 	}
 	
 // Method to click for org
   	public void clicksetAdminLastName() {
   		wait.until(ExpectedConditions.elementToBeClickable(setAdminLastName)).click();
   	}
   	
   	//Method to set name
 	public void setAdminLastName(String pn) {
 		driver.findElement(setAdminLastName).sendKeys(pn);
 	}
 	
 	
 // Method to click for org
   	public void clicksetAdminContactNum() {
   		wait.until(ExpectedConditions.elementToBeClickable(setAdminContactNumber)).click();
   	}
   	
   	//Method to set name
 	public void setAdminContactNum(String pn) {
 		driver.findElement(setAdminContactNumber).sendKeys(pn);
 	}
    
    // Method to click for mail org
   	public void clicksetAdmingMail() {
   		wait.until(ExpectedConditions.elementToBeClickable(setAdmingMail_text)).click();
   	}

	//Method to set mail
	public void clicksetAdmingMail(String pn) {
		driver.findElement(setAdmingMail_text).sendKeys(pn);
	}
    
    // Method to click draft organization
    public void clicksaveCreateOrgBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(saveCreate_orgBtn)).click();
    }
     
 
    public boolean isOrgDisplayed(String orgName) {
        try {
            By orgResult = By.xpath(String.format("//table//td[contains(normalize-space(), '%s')]", orgName));
            WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(orgResult));
            return result.isDisplayed() && result.getText().contains(orgName);
        } catch (Exception e) {
            System.out.println("Failed to find org '" + orgName + "': " + e.getMessage());
            return false;
        }
    }
    
}