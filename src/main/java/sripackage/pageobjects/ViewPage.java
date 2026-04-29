package sripackage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sripackage.AbstractComponents.AbstractComponents;

public class ViewPage extends AbstractComponents {

	WebDriver driver;
	public ViewPage(WebDriver driver) {
		super(driver);// passing driver from child to parent class ie AbstractComponent class
		// initialization
		this.driver = driver; // giving life to current class driver, driver coming from base class

		PageFactory.initElements(driver, this);// initializing and defining all @findBy with driver
	}
	
	@FindBy(xpath = "//a[text()='Continue Shopping']")
	WebElement continueShoppingButton;
	
	@FindBy(xpath = "//div[contains(@class,'col-lg')]/a/following-sibling::div/h2")
	WebElement productNameInView;
	
	@FindBy(xpath = "//div[contains(@class,'col-lg')]/a/following-sibling::div/h3")
	WebElement priceInView;
	
	public String getProductNameInViewPage() {
		waitForWebElementToAppear(continueShoppingButton);
		return productNameInView.getText();
	}
	public int getProductPriceInViewPage() {
		waitForWebElementToAppear(continueShoppingButton);
		System.out.println("price in view:"+extractPriceFromText(priceInView.getText()));
		return extractPriceFromText(priceInView.getText());
	}
	private int extractPriceFromText(String priceText) {
	    // "$ 11500" → remove $ and spaces → "11500" → parse to int
	    return Integer.parseInt(priceText.replace("$", "").trim());//$ 676=>676
	}
	

}
