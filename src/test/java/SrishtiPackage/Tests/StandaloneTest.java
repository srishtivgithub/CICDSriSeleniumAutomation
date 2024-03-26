package SrishtiPackage.Tests;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		ChromeOptions options = new ChromeOptions();
		options.addArguments("force-device-scale-factor=0.75");
		options.addArguments("high-dpi-support=0.75");
		
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		 WebDriverWait w=new WebDriverWait(driver, Duration.ofSeconds(7));
		 
		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("dummyemailsrishti@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Dummyemail2@");
		driver.findElement(By.id("login")).click();
		Thread.sleep(4000);
		/*
		 * WebElement html = driver.findElement(By.tagName("html"));
		 * html.sendKeys(Keys.chord(Keys.CONTROL,Keys.SUBTRACT));
		 * html.sendKeys(Keys.chord(Keys.CONTROL,Keys.SUBTRACT));
		 */
		//scrolling down the page
		JavascriptExecutor js=(JavascriptExecutor)driver;//casting driver
		  js.executeScript("window.scrollBy(0,200)");//scrolling window/browser/wholepage 
		  Thread.sleep(3000);

		List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
		

		/*
		 * //adding to cart using for loop for(int i=0;i<products.size();i++) {
		 * 
		 * String
		 * productName=products.get(i).findElement(By.cssSelector("div.card-body b")).
		 * getText(); System.out.println(productName);
		 * if(productName.equals("ADIDAS ORIGINAL")) { WebElement
		 * addToCart=products.get(i).findElement(By.
		 * cssSelector("div.card-body button:last-of-type"));
		 * js.executeScript("arguments[0].click();", addToCart);
		 * //products.get(i).findElement(By.
		 * cssSelector("div.card-body button:last-of-type")).click(); break; }
		 * 
		 * }
		 */
		
		
		//using stream to retrive webelement of one product tile
		
		//in filter we are finding the product text
		//there may be many products with same name=ADIDAS ORIGINAL, therefore 
		//gave findFirst()->which will select 1st selection else will return null if nothing found
		
		//as text contains inside cssSelector("div.mb-3") thus we smartly used product
		//instead of driver , so it will search for p tag within the block product
		//p tag only contains the product text
		/*
		 * JavascriptExecutor js=(JavascriptExecutor)driver;//casting driver
		 * js.executeScript("window.scrollBy(0,300)");//scrolling
		 * window/browser/wholepage Thread.sleep(3000);
		 */
		 
		  
			//adding item to cart using streams API
			  WebElement prod=products.stream().filter(product
			  ->product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL"
			  )) .findFirst().orElse(null); 
			  
			  //click on view button
			   //prod.findElement(By.cssSelector(".card-body button:first-of-type")).click();
			   
			  //click on Add to cart button
			
			   //below is not working and giving Element Intercept exception
			  //prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
			 
			  //thus using javascript to click on add to cart button
			  WebElement addToCart=prod.findElement(By.cssSelector("div.card-body button:last-of-type"));
			  js.executeScript("arguments[0].click();", addToCart);
			  
			  //checking green banner saying:product added to cart
			  w.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
			  
			  //loading spinner wait to disappear so that we can open Cart in header
			  w.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
			  Thread.sleep(3000);
			  js.executeScript("window.scrollTo(0,0)");//scrolling up the page
			  Thread.sleep(3000);
			  w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[routerlink*='cart']")));
			  
			  //driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
			  WebElement clickAddToCart=prod.findElement(By.cssSelector("button[routerlink*='cart']"));
			  Thread.sleep(3000);
			  js.executeScript("arguments[0].click();", clickAddToCart);
	
	}

}
