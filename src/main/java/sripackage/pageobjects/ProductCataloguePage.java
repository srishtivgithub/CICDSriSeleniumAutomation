package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import sripackage.AbstractComponents.AbstractComponents;

public class ProductCataloguePage extends AbstractComponents {

	WebDriver driver;
	
	public ProductCataloguePage(WebDriver driver) {
		super(driver);//passing driver from child to parent class ie AbstractComponent class
		//initialization
		this.driver=driver; //giving life to current class driver, driver coming from base class
		
		PageFactory.initElements(driver, this);//initializing and defining all @findBy with driver
	}
	
	//List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productBy=By.cssSelector(".mb-3");
	By addToCart=By.cssSelector(".card-body button:last-of-type");
	By toastContainer=By.id("toast-container");
	
	public List<WebElement> getProductList() {
		waitForElementToAppear(productBy);
		return products;
	}
	
	public WebElement getProductByName(String prodName) {
		
		WebElement prod = getProductList().stream()
		.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(prodName))
		.findFirst().orElse(null);
		
		return prod;
	}
	
	public void addProducttoCart(String prodName) {
		//note:we cannot write pagefactory for below css element as its prod.findElement()
		//and not driver.findEleemnt()
		//thus use By addToCart=By.cssSelector(".card-body button:last-of-type");
		//instead of @FindBy 
		
		//prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		WebElement prod=getProductByName(prodName);
		prod.findElement(addToCart).click();
		
		// checking green banner saying:product added to cart
		//Below we cant use pagefactory thus initialize using By,since its not driver by
		//w.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		waitForElementToAppear(toastContainer);
		
		//for below used @findBy Page factory as it is driver.findElement
		//w.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		waitForElementToBeInvisible(spinner);
		
		
	}
	
	

}
