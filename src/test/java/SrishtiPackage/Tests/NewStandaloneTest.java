package SrishtiPackage.Tests;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import sripackage.pageobjects.LandingPage;

public class NewStandaloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// zooming out the browser-to make all elements visible
		ChromeOptions options = new ChromeOptions();
		options.addArguments("force-device-scale-factor=0.75");
		options.addArguments("high-dpi-support=0.75");

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(7));

		LandingPage lp=new LandingPage(driver);
		driver.get("https://rahulshettyacademy.com/client");

		driver.findElement(By.id("userEmail")).sendKeys("dummyemailsrishti@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Dummyemail2@");
		driver.findElement(By.id("login")).click();
		Thread.sleep(4000);
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		
		/*
		 * //1. adding to cart using for loop for(int i=0;i<products.size();i++) {
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
		
		//2. using stream to retrive webelement of one product tile and adding it to cart

		// in filter we are finding the product text
		// there may be many products with same name=ADIDAS ORIGINAL, therefore
		// gave findFirst()->which will select 1st selection else will return null if
		// nothing found

		// as text contains inside cssSelector("div.mb-3") thus we smartly used product
		// instead of driver , so it will search for p tag within the block product
		// p tag only contains the product text

		String prodName="ADIDAS ORIGINAL";
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(prodName))
				.findFirst().orElse(null);

		//click on add to cart button for addidas item
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		// checking green banner saying:product added to cart
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));

		// loading spinner wait to disappear so that we can open Cart in header
		w.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		//click on Cart button in header
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		
		//check if the productname in cart is same as product which was added to cart using Streams
		List<WebElement> cartProducts=driver.findElements(By.xpath("//*[@class='cartSection']//h3"));
		
		Boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equals(prodName));
		Assert.assertTrue(match);
		
		//click on checkout button
		driver.findElement(By.cssSelector("div[class*='subtotal'] button[class*='btn-primary']")).click();
		
		//selecting country from auto suggest drop down
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("India");
		
		w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[class*='list-group']")));
		List<WebElement> dropdownOptions=driver.findElements(By.cssSelector("button[class*='list-group-item'] span"));
		
		WebElement option=dropdownOptions.stream().filter(dropdownOption->dropdownOption.getText().contains("India"))
		.findFirst().orElse(null);
		option.click();
		
		//click on placeorder button
		driver.findElement(By.cssSelector("a[class*='action__submit']")).click();
		
		String confirmMessage=driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		driver.quit();

	}

}
