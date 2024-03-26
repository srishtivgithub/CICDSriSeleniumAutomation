package sripackage.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.OrdersPage;

public class AbstractComponents {

	//class for reusable code for every class.
	//this class must be inherited by all classes to use reusable code
	
	WebDriver driver;
	
	public AbstractComponents(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="button[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/myorders']")
	WebElement ordersHeader;
	
	public void waitForElementToAppear(By findBy) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(7));
		w.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForWebElementToAppear(WebElement findBy) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(7));
		w.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	public void waitForElementToBeInvisible(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(7));
		w.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public void sleep() throws InterruptedException {
		Thread.sleep(3000);
	}
	
	public CartPage goToCartPage() {
		//click on Cart button in header
		//driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		
		//note:as addcart button is common thus we used it in abstract class
		cartHeader.click();
		return new CartPage(driver);
				
	}
	
	public OrdersPage goToOrders() {
		waitForWebElementToAppear(ordersHeader);
		ordersHeader.click();
		return new OrdersPage(driver);
	}
}
