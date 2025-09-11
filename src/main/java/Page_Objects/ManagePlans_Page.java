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

public class ManagePlans_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//li[5]//a[1]//span[1]");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By link_create = By.xpath("//a[contains(text(),'Create')]");
    By input_plan_name = By.xpath("//input[@placeholder='Enter Plan Name']");
    By select_plan_typeDropdown = By.xpath("//select[@name='planType']");
    By select_plan_typeChoose = By.xpath("//*[@id='root']/div/div[3]/div/form/div/div/div/div[2]/div[2]/select/option[3]");
    By btn_create = By.xpath("//button[normalize-space()='Create']");
    By btn_ok1 = By.xpath("//button[contains(text(),'Ok')]");
    By input_search_plans = By.xpath("//input[@placeholder='Search for plans']");
    By div_plan_a = By.xpath("//div[contains(text(),'Plan A')]");
    By btn_update = By.xpath("//button[normalize-space()='Update']");
    By btn_ok2 = By.xpath("//button[contains(text(),'Ok')]");
    By btn_filters = By.xpath("//button[normalize-space()='Filters']");
    By header_status = By.xpath("//h4[normalize-space()='Status']");
    By label_draft = By.xpath("//label[normalize-space()='Draft']");
    By div_content_area = By.xpath("//div[@class='contentArea']");
    By svg_icon = By.xpath("//svg");
    By header_mosharof = By.xpath("//h3[normalize-space()='Mosharof Hossain']");
    By img_avatar = By.xpath("//div[@class='avatar']//img[@alt='Avatar']");

    // Constructor
    public ManagePlans_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Reduced timeout
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }

    // Method to click Manage Plans
    public void clickManagePlans() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_plans)).click();
    }

    // Method to click Create link
    public void clickCreateLink() {
        wait.until(ExpectedConditions.elementToBeClickable(link_create)).click();
    }

    // Method to enter Plan Name
    public void enterPlanName(String planName) {
        WebElement planNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(input_plan_name));
        planNameInput.clear();
        planNameInput.sendKeys(planName);
    }

    // Method to click Plan Type dropdown
    public void clickPlanTypeDropdown() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(select_plan_typeDropdown));
        try {
            dropdown.click();
        } catch (Exception e) {
            System.out.println("Standard click failed, trying JavaScript click: " + e.getMessage());
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", dropdown);
        }
    }

    // Method to select Plan Type (User based or Organization based)
    public void selectPlanType(String planType) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(select_plan_typeDropdown));
        try {
            // Try Select class for <select> dropdown
            Select select = new Select(dropdown);
            select.selectByVisibleText(planType); // e.g., "User based"
        } catch (Exception e) {
            // Fallback: Click the specific option (User based)
            System.out.println("Select class failed, trying direct option click: " + e.getMessage());
            wait.until(ExpectedConditions.elementToBeClickable(select_plan_typeChoose)).click();
        }
    }

    // Method to get available dropdown options for debugging
    public List<String> getPlanTypeOptions() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(select_plan_typeDropdown));
        try {
            Select select = new Select(dropdown);
            return select.getOptions().stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Unable to get options with Select, trying custom dropdown: " + e.getMessage());
            List<WebElement> options = driver.findElements(By.xpath("//*[@id='root']/div/div[3]/div/form/div/div/div/div[2]/div[2]/select/option"));
            return options.stream().map(WebElement::getText).collect(Collectors.toList());
        }
    }

    // Method to click Create button
    public void clickCreateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_create)).click();
    }

    // Method to click first Ok button (after Create)
    public void clickOkAfterCreate() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok1)).click();
    }

    // Method to search for plans
    public void searchPlans(String searchText) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(input_search_plans));
        searchInput.clear();
        searchInput.sendKeys(searchText);
    }

    // Method to verify Plan A exists
    public boolean isPlanADisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(div_plan_a)).isDisplayed();
    }

    // Method to update Plan Name
    public void updatePlanName(String updatedPlanName) {
        WebElement planNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(input_plan_name));
        planNameInput.clear();
        planNameInput.sendKeys(updatedPlanName);
    }

    // Method to click Update button
    public void clickUpdateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_update)).click();
    }

    // Method to click second Ok button (after Update)
    public void clickOkAfterUpdate() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok2)).click();
    }

    // Method to click Filters button
    public void clickFiltersButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_filters)).click();
    }

    // Method to verify Status header
    public boolean isStatusHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header_status)).isDisplayed();
    }

    // Method to select Draft status
    public void selectDraftStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_draft)).click();
    }

    // Method to verify Content Area
    public boolean isContentAreaDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(div_content_area)).isDisplayed();
    }

    // Method to verify SVG icon
    public boolean isSvgIconDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(svg_icon)).isDisplayed();
    }

    // Method to verify Mosharof Hossain header
    public boolean isMosharofHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header_mosharof)).isDisplayed();
    }

    // Method to verify Avatar image
    public boolean isAvatarImageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(img_avatar)).isDisplayed();
    }
}