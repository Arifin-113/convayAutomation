package Page_Objects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_Page {

	WebDriver driver;

	// Constructors
	public Login_Page(WebDriver driver) 
	{
		this.driver = driver;
	}

	// Locators
	
	By txt_username_loc = By.name("signInMail");
	By txt_password_loc = By.name("password");
	By btn_login_loc = By.name("sign_up_submit");
	By ContinueWithEmail = By.xpath("//div[@class='SignIn_signInWithEmail__1V-st']");

	public By txt_mail_loc = By.xpath("//input[@id='identifierId']");
	public By btn_next = By.xpath("//span[normalize-space()='Next']");
//	public By btn_nextB = By.xpath("//span[contains(text(),'পরবর্তী')]");
	public By txt_pass_loc = By.xpath("//input[@name='Passwd']");
	public By btn_next2 = By.xpath("//span[normalize-space()='Next']");
//	public By btn_next2B = By.xpath("//span[contains(text(),'পরবর্তী')]");
	
	//div[@class='riDSKb']
	public By btn_useranother = By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/main/div[2]/div/div/div[1]/form/span/section/div/div/div/div/ul/li[3]/div/div/div[2]");
	//*[@id="yDmH0d"]/c-wiz/main/div[2]/div/div/div[1]/form/span/section/div/div/div/div/ul/li[3]/div/div/div[2]
	
	//Already signed in
	//*[@id="yDmH0d"]/c-wiz/main/div[2]/div/div/div[1]/form/span/section/div/div/div/div/ul/li[2]/div/div[1]/div/div[2]
	public By ContinuewWithGoogle = By.xpath("//span[normalize-space()='Continue with Google']");


	// Action methods
	public void setUserName(String user) {
		driver.findElement(txt_username_loc).sendKeys(user);
	}
	
	

	public void setPassword(String pwd) {
		driver.findElement(txt_password_loc).sendKeys(pwd);
	}

	public void clickLogin() {
		driver.findElement(btn_login_loc).click();
	}
	
	public void clickContinueWithEmail() {
		driver.findElement(ContinueWithEmail).click();
	}
	
	
	public void clickUseAnother() {
		
		driver.findElement(btn_useranother).click();
		}
	
	public void setMail(String mail) {
		driver.findElement(txt_mail_loc).sendKeys(mail);
		
	}
	
	public void clicknext() {
		
		driver.findElement(btn_next).click();
		}
	

	
	public void setPass(String pass) {
		driver.findElement(txt_pass_loc).sendKeys(pass);
		
	}
	
public void clicknext2() {
		
		driver.findElement(btn_next2).click();
		}



	public void ContinuewWithGoogle() {
		driver.findElement(ContinuewWithGoogle).click();
		
	}

}