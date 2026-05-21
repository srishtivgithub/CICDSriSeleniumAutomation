package sripackage.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
	
	@FindBy(xpath = "//button[contains(text(),'Sign Out')]")
	    WebElement signOutButton;
	
	@FindBy(xpath = "//button[text()=' HOME ']")
    WebElement homeHeader;

//	public abstract boolean isPageLoaded();
	   
	public boolean isUserLoggedIn() {
		waitForWebElementToAppear(signOutButton);
		return signOutButton.isDisplayed();
	}
	public boolean isHomeHeaderVisible() {
		waitForWebElementToAppear(homeHeader);
		return homeHeader.isDisplayed();
	}
	public boolean isOrdersHeaderVisible() {
		waitForWebElementToAppear(ordersHeader);
		return ordersHeader.isDisplayed();
	}
	public boolean isCartHeaderVisible() {
		waitForWebElementToAppear(cartHeader);
		return cartHeader.isDisplayed();
	}
	public void clickHomeTab() {
		waitForWebElementToAppear(homeHeader);
		homeHeader.click();
	}
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
	public void waitForUrlToLoad(String urlResource) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(urlResource));
        
	}
	public void frameISAvailable(By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        
	}
	
	public void waitForVisibilityOfAllListOfWebElements(List<WebElement> elementList) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
        
        
	}
	public void waitForVisibilityOfAllElementsLocatedBy(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        
	}
	public void waitForTextToChangeInElement(WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, text)));
	}
	
	public void sleep() throws InterruptedException {
		Thread.sleep(3000);
	}
	
	public CartPage goToCartPage() {
		//click on Cart button in header
		//driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		
		//note:as addcart button is common thus we used it in abstract class
		waitForWebElementToAppear(cartHeader);
		cartHeader.click();
		return new CartPage(driver);
				
	}
	
	public OrdersPage goToOrders() {
		waitForWebElementToAppear(ordersHeader);
		ordersHeader.click();
		return new OrdersPage(driver);
	}
	public void selectFromDropdownByVisibleText(WebElement element, String text) {
		Select s=new Select(element);
		s.selectByVisibleText(text);
	}
	public void selectFromDropdownByValue(WebElement element, String value) {
	    new Select(element).selectByValue(value);
	    // selectByValue matches the 'value' HTML attribute
	    // e.g. <option value="1: Doctor">Doctor</option> → pass "1: Doctor"
	}

	public void selectFromDropdownByIndex(WebElement element, int index) {
	    new Select(element).selectByIndex(index);
	    // selectByIndex → 0-based → index 0 = first option
	}
	public List<WebElement> getDropdownOptions(WebElement element) {
	    return new Select(element).getOptions();
	    // use this to ASSERT which option is currently selected
	}
	
	public void getDropdownOptionByIndex(WebElement element, int index) {
	    new Select(element).selectByIndex(index);
	    // selectByIndex → 0-based → index 0 = first option
	}
	public Boolean isElementPresent(WebElement element) {
		
		 try {
		        return element.isDisplayed();
		    } catch (NoSuchElementException | StaleElementReferenceException e) {
		        return false;
		    }
	}
	
	public String getCurrentUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}
	
}

