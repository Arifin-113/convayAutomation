package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_OrganizationSearch_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By org = By.xpath("//span[normalize-space()='Organizations']"); 
    By input_search_org = By.xpath("//input[@placeholder='Search']"); 
 
    By setorgName_text = By.xpath("//input[@placeholder='Search']");
//    By div_org = By.xpath("//tbody/tr[1]/td[1]/div[1]/div[1]");
    By orgResult = By.xpath("//div[contains(text(), 'organization')]");

    
    // Constructor
    public CM_OrganizationSearch_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    }


 // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }

    // Method to search for plans 
    public void clickSearchOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(input_search_org)).click();
    }
   
    
    public void setorgName(String user) {
        try {
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(setorgName_text));
            System.out.println("Search input found: " + searchInput.isDisplayed());
            searchInput.clear();
            searchInput.sendKeys(user);
            searchInput.sendKeys(Keys.ENTER); // Submit the search
        } catch (Exception e) {
            System.out.println("Failed to set plan name: " + e.getMessage());
            throw e;
        }
    }
     
/*
    // Method to verify org exists
    public boolean isOrgDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(div_org)).isDisplayed();
    }
*/
    
    public boolean isOrgDisplayed(String orgName) {
        try {
            WebElement result = driver.findElement(orgResult);
            return result.isDisplayed() && result.getText().contains(orgName);
        } catch (Exception e) {
            return false; // Return false if element not found
        }
    }
   
}