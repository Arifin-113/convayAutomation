package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CM_OrganizationCreateNew_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By org = By.xpath("//span[normalize-space()='Organizations']");
    By link_create = By.xpath("//a[@class='btnPrimary']");
    By superadmin = By.cssSelector("//div[contains(text(),'Super Admin')]");
    
    By btn_create = By.xpath("//button[normalize-space()='Create']");
    By btn_ok1 = By.xpath("//button[contains(text(),'Ok')]");
    

    // Constructor
    public CM_OrganizationCreateNew_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Default timeout
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }

    // Method to click Manage Plans
    public void clickOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(org)).click();
    }

    // Method to click Create link
    public void clickCreateLink() {
        wait.until(ExpectedConditions.elementToBeClickable(link_create)).click();
        // Add small delay to ensure page loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void clickSuperadmin() {
        wait.until(ExpectedConditions.elementToBeClickable(superadmin)).click();
    }
    


    // Method to click Create button
    public void clickCreateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_create)).click();
    }

    // Method to click first Ok button (after Create)
    public void clickOkAfterCreate() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok1)).click();
    }

    

    
}