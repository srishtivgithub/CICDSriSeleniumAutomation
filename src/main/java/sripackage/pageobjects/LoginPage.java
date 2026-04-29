package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sripackage.AbstractComponents.AbstractComponents;
import sripackage.resources.JavascriptUtility;

public class LoginPage extends AbstractComponents {
	WebDriver driver;
	JavascriptUtility jsUtil;
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		this.jsUtil=new JavascriptUtility(driver);
		PageFactory.initElements(driver, this);
	}

	
	
	@FindBy(xpath="//div[@aria-label='Incorrect email or password.']")
	WebElement incorrectCredentialToast;
	
	@FindBy(xpath="//div[text()='*Enter Valid Email']")
	List<WebElement> validEmailFormatMsg;
	
	@FindBy(xpath="//div[text()='*Email is required']")
	List<WebElement> requiredEmailMsg;
	
	@FindBy(xpath="//div[text()='*Password is required']")
	List<WebElement> requiredPasswordMsg;
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(css="a[class='forgot-password-link']")
	WebElement forgotPasswordLink;
	
	@FindBy(css="p[class='login-wrapper-footer-text']")
	WebElement registerHereLink;
	
	public void clickForgotPasswordLink() {
		
		forgotPasswordLink.click();
	}
	public void clickRegisterHereLink() {
		registerHereLink.click();
	}
	public String validateForgotPasswordPageUrl() {
		waitForUrlToLoad("password-new");
		String url=getCurrentUrl();
		return url;
	}
	public String validateRegisterPageUrl() {
		waitForUrlToLoad("register");
		String url=getCurrentUrl();
		return url;
	}
	public void enterEmailPasswordinLogin(String uname, String pwd) {
		userEmail.sendKeys(uname);
		password.sendKeys(pwd);
	}
	
	public String getIncorrectCredentialToastText() {
		waitForWebElementToAppear(incorrectCredentialToast);
		return incorrectCredentialToast.getAttribute("aria-label");
	}
	
	public String getInlineValidationError() {
		
		if(!validEmailFormatMsg.isEmpty()) {
			return validEmailFormatMsg.get(0).getText().trim();
		}
		if(!requiredEmailMsg.isEmpty()) {
			return requiredEmailMsg.get(0).getText().trim();
		}
		if(!requiredPasswordMsg.isEmpty()) {
			return requiredPasswordMsg.get(0).getText().trim();
		}
		
		return "";
	}
	public String getRequiredEmailError() {
		if(!requiredEmailMsg.isEmpty()) {
			return requiredEmailMsg.get(0).getText().trim();
		}
		return "";
	}
	public String getRequiredPasswordError() {
		if(!requiredPasswordMsg.isEmpty()) {
			return requiredPasswordMsg.get(0).getText().trim();
		}
		return "";
	}

}
