package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


import sripackage.AbstractComponents.AbstractComponents;

public class OrdersPage extends AbstractComponents {

	WebDriver driver;
	
	public OrdersPage(WebDriver driver) {
		super(driver);//passing driver from child to parent class ie AbstractComponent class
		//initialization
		this.driver=driver; //giving life to current class driver, driver coming from base class
		
		PageFactory.initElements(driver, this);//initializing and defining all @findBy with driver
	}
	
	
	@FindBy(css="tr td:nth-of-type(2)")
	List<WebElement> orderProducts;

	
	public Boolean verifyProductInOrdersPage(String prodName) {
		//check if the productname in Orders is same as product which was added to cart using Streams
		Boolean match=orderProducts.stream().anyMatch(orderProduct->orderProduct.getText().equalsIgnoreCase(prodName));
		return match;
	}
	
	
	
	
	
	

}
