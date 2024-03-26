package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


import sripackage.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);//passing driver from child to parent class ie AbstractComponent class
		//initialization
		this.driver=driver; //giving life to current class driver, driver coming from base class
		
		PageFactory.initElements(driver, this);//initializing and defining all @findBy with driver
	}
	
	
	@FindBy(xpath="//*[@class='cartSection']//h3")
	List<WebElement> cartProducts;
	
	@FindBy(css="div[class*='subtotal'] button[class*='btn-primary']")
	WebElement checkOutButton;
	
	public Boolean verifyProductDisplay(String prodName) {
		//check if the productname in cart is same as product which was added to cart using Streams
		Boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equals(prodName));
		return match;
	}
	
	public CheckOutPage clickCheckOut() {
		//driver.findElement(By.cssSelector("div[class*='subtotal'] button[class*='btn-primary']")).click();
		checkOutButton.click();
		
		return new CheckOutPage(driver);
	}
	

	
	
	
	

}
