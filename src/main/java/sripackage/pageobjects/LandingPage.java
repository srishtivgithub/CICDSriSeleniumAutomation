package sripackage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sripackage.AbstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents{

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		
		super(driver); //passing driver from child to parent class ie AbstractComponent class
		//initialization
		this.driver=driver; //giving life to current class driver, driver coming from base class
		
		PageFactory.initElements(driver, this);//initializing and defining all @findBy with driver
	}
	
	//WebElement userEmail=driver.findElement(By.id("userEmail"));
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ProductCataloguePage loginApplication(String uname, String pwd) {
		userEmail.sendKeys(uname);
		password.sendKeys(pwd);
		submit.click();
		ProductCataloguePage pc=new ProductCataloguePage(driver);
		return pc; //intead of creating object in other class,
		//sending object from here itself
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		String errMsg=errorMessage.getText();
		return errMsg;
	}
	

}
