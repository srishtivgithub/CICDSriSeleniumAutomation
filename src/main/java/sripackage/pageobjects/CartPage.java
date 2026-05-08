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
	
	@FindBy(xpath="//h1[text()='No Products in Your Cart !']")
	WebElement emptyCartMsg;
	
	By cartRows=By.xpath("//div[@class='infoWrap']");
	By nameInCartRow=By.xpath("//div[@class='infoWrap']//following::h3");
	By deleteButtonInCartRow=By.xpath("//div[@class='infoWrap']//following::button[contains(@class,'danger')]");
	
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
	public boolean verifyProductinCartPage(String productName) {
		for(int i=0;i<cartProducts.size();i++) {
			if(cartProducts.get(i).getText().equalsIgnoreCase(productName)) {
				return true;
			}
		}
		return false;
	}
	public List<WebElement> getCartRows() {
	
	
		
		waitForElementToAppear(cartRows);
		return driver.findElements(cartRows);
	}
	public void getcartProductCount() {
		
	}
	
	public void deleteItemInCart(String productName) {
		List<WebElement> cartRows=getCartRows();
		
		for(int i=0;i<cartRows.size();i++) {
			if(cartRows.get(i).findElement(nameInCartRow).getText().equalsIgnoreCase(productName)) {
				cartRows.get(i).findElement(deleteButtonInCartRow).click();
				//verify red banner
			}
		}
	}
	public boolean isProductDeletedFromCart(String productName) throws InterruptedException {
		Thread.sleep(3000);
        List<WebElement> cartRows=getCartRows();
		
		for(int i=0;i<cartRows.size();i++) {
			if(cartRows.get(i).findElement(nameInCartRow).getText().equalsIgnoreCase(productName)) {
				return false;
			}
		}
		return true;
	}

	

	
	
	
	

}
