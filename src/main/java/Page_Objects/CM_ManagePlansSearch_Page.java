package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_ManagePlansSearch_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By input_search_plans = By.xpath("//input[@placeholder='Search for plans']"); 
 
    By setPlanName_text = By.xpath("//input[@placeholder='Search for plans']");
    By div_plan_a = By.xpath("//div[contains(text(), 'Plan A')]");
    By div_plan_info = By.xpath("//h3[contains(text(), 'Plan')]");
    
    // Constructor
    public CM_ManagePlansSearch_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15)); 
            WebElement manageOrg = longWait.until(ExpectedConditions.elementToBeClickable(manage_org));
            System.out.println("Manage Org found: " + manageOrg.isDisplayed());
            manageOrg.click();
        } catch (Exception e) {
            System.out.println("Failed to click Manage Org: " + e.getMessage());
            throw e;
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to click Manage Plans
    public void clickManagePlans() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_plans)).click();
    }

    // Method to search for plans 
    public void clickSearchPlans() {
        wait.until(ExpectedConditions.elementToBeClickable(input_search_plans)).click();
    }
   
    
    public void setPlanName(String user) {
        try {
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(setPlanName_text));
            System.out.println("Search input found: " + searchInput.isDisplayed());
            searchInput.clear();
            searchInput.sendKeys(user);
            searchInput.sendKeys(Keys.ENTER); // Submit the search
        } catch (Exception e) {
            System.out.println("Failed to set plan name: " + e.getMessage());
            throw e;
        }
    }
     

    // Method to verify Plan A exists
    public boolean isPlanADisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(div_plan_a)).isDisplayed();
    }
    
    
    // Method to click Plan A to open edit form
    public void clickPlanA() {
        WebElement planA = wait.until(ExpectedConditions.elementToBeClickable(div_plan_a));
        planA.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
   
}