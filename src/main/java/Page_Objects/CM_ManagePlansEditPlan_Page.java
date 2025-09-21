package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_ManagePlansEditPlan_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By input_plan_name = By.cssSelector("input[placeholder='Enter Plan Name']");
    By input_search_plans = By.xpath("//input[@placeholder='Search for plans']");
    By div_plan_a = By.xpath("//div[contains(text(), 'Plan A')]");
    By div_plan_info = By.xpath("//h3[contains(text(), 'Plan')]");
    By edit_btn = By.xpath("//*[@id=\"root\"]/div/div[3]/div/div[1]/div[2]/button[2]");
    By select_plan_typeChooseOrg = By.xpath("//*[@id='root']/div/div[3]/div/form/div/div/div/div[2]/div[2]/select/option[2]");
    By btn_update = By.xpath("//button[normalize-space()='Update']");
    By btn_ok2 = By.xpath("//button[contains(text(), 'Ok')]");
    By edit_btn2 = By.xpath("//*[@id=\"root\"]/div/div[3]/div[2]/div[3]/div/table/tbody/tr[2]/td[6]/div/button[1]");
    
    // Constructor
    public CM_ManagePlansEditPlan_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased default timeout
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout
            WebElement manageOrg = longWait.until(ExpectedConditions.elementToBeClickable(manage_org));
            System.out.println("Manage Org found: " + manageOrg.isDisplayed());
            manageOrg.click();
        } catch (Exception e) {
            System.out.println("Failed to click Manage Org: " + e.getMessage());
            throw e;
        }
        // Add delay to ensure page stability
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

    // Method to search for plans and press Enter
    public void searchPlans(String searchText) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(input_search_plans));
        searchInput.clear();
        searchInput.sendKeys(searchText);
        searchInput.sendKeys(Keys.ENTER); // Press Enter key
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


    // Method to verify Plan Info header
    public boolean isPlanInfoDisplayed() {
        try {
            WebElement planInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(div_plan_info));
            System.out.println("Plan Info found: " + planInfo.getText());
            return planInfo.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to find Plan Info: " + e.getMessage());
            return false;
        }
    }
    
    
    
    
 // Method to Edit Plan A to open edit form
    public void editButton() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(edit_btn));
        editButton.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 // Method to Edit Plan A to open edit form
    public void editButton2() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(edit_btn));
        editButton.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 // Method to choose org
    public void typeChooseOrg() {
        WebElement typeChooseOrg = wait.until(ExpectedConditions.elementToBeClickable(select_plan_typeChooseOrg));
        typeChooseOrg.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

    // Method to update Plan Name
    public void updatePlanName(String updatedPlanName) {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased timeout
            WebElement planNameInput = longWait.until(ExpectedConditions.visibilityOfElementLocated(input_plan_name));
            System.out.println("Plan name input found: " + planNameInput.isDisplayed());
            planNameInput.clear();
            planNameInput.sendKeys(updatedPlanName);
            planNameInput.sendKeys(Keys.ENTER); // Press Enter key
        } catch (Exception e) {
            // Fallback XPath
            System.out.println("CSS selector failed, trying fallback XPath: " + e.getMessage());
            By fallbackInput = By.xpath("//input[@type='text' and contains(@placeholder, 'Plan Name')]");
            WebElement planNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(fallbackInput));
            planNameInput.clear();
            planNameInput.sendKeys(updatedPlanName);
            planNameInput.sendKeys(Keys.ENTER); // Press Enter key
        }
    }

    // Method to click Update button
    public void clickUpdateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_update)).click();
    }

    // Method to click second Ok button (after Update)
    public void clickOkAfterUpdate() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok2)).click();
    }

}
