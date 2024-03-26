package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import sripackage.AbstractComponents.AbstractComponents;

public class ConfirmationPage extends AbstractComponents {

	WebDriver driver;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		// initialization
		this.driver = driver; // giving life to current class driver, driver coming from base class

		PageFactory.initElements(driver, this);// initializing and defining all @findBy with driver
	}

	@FindBy(xpath = "//h1[@class='hero-primary']")
	WebElement confirmationMsg;

	public String getConfirmationMessage() throws InterruptedException {
		//sleep();
		//String confirmMessage=driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
		String msg=confirmationMsg.getText();
		return msg;
	}
}
