package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import sripackage.AbstractComponents.AbstractComponents;

public class ProductCataloguePage extends AbstractComponents {

	WebDriver driver;

	public ProductCataloguePage(WebDriver driver) {
		super(driver);// passing driver from child to parent class ie AbstractComponent class
		// initialization
		this.driver = driver; // giving life to current class driver, driver coming from base class

		PageFactory.initElements(driver, this);// initializing and defining all @findBy with driver
	}

	// List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinner;

	@FindBy(css = "app-dashboard[class='ng-star-inserted']")
	WebElement productCataloguePage;

	@FindBy(xpath = "//div[not(contains(@class,'p-4'))]/input[@name='search' and @placeholder='search']")
	WebElement productSearchBar;

	@FindBy(xpath = "//div[contains(@class,'col-lg-4 ')]")
	List<WebElement> productCards;

	@FindBy(xpath = "//div[contains(@class,'col-lg-4 ')]//following::b")
	List<WebElement> productNamesInCard;
	
	@FindBy(xpath = "//div[@class='card-body']//b//parent::h5//following-sibling::button[2]")
	List<WebElement> addToCartButtons;
	
	//div[@class='card-body']//b//parent::h5//following-sibling::button[2]

	@FindBy(xpath = "//div[@id='res']")
	WebElement showResults;

	@FindBy(xpath = "//div[not(contains(@class,'mb-1'))]/input[@name='minPrice']")
	WebElement minPriceRange;

	@FindBy(xpath = "(//input[@name='maxPrice'])[2]")
	WebElement maxPriceRange;
	
	@FindBy(css="button[routerlink='/dashboard/cart'] label")
	WebElement cartHeaderCount;
	
	@FindBy(xpath = "//button[text()=' View']")
	WebElement viewButton;
	
	@FindBy(xpath = "//div[@class='card-body']//b")
	WebElement productName;
	
	@FindBy(xpath="//div[@class='card-body']")
	WebElement productMiniCards;
	
	@FindBy(xpath = "//div[@class='card-body']//div/div")
	WebElement productPrice;
		

	By productBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastContainer = By.id("toast-container");
	By productCardsBy = By.cssSelector("div[class*='col-lg-4 ']");
	By productPriceBy=By.xpath("//div[@class='card-body']/div/div");
	By productElement=By.xpath("//div[contains(@class,'col-lg-4 ')]//b");
	

	public boolean showResultCount() {
		String str=showResults.getText();
		String[] arr=str.split(" ");
		int count=Integer.parseInt(arr[1]);
		if(count==0) {
			System.out.println("0 items found");
			return false;
		}
		else {
			System.out.println(count+" items found");
			return true;
		}
	}
	//return 1st product card name
	public String getProductName() {
		waitForElementToAppear(productBy);
		return productName.getText();
	}
	//return 1st product card price
	public int getProductPrice() {
		waitForElementToAppear(productBy);
		return extractPriceFromText(productPrice.getText());
	}
	public String getProductPriceWithCurrency() {
		waitForElementToAppear(productBy);
		return productPrice.getText();
	}
	
	public boolean isProductCataloguePageOpen() {
		waitForWebElementToAppear(productCataloguePage);
		return productCataloguePage.isDisplayed();
	}
	public void clickOnView() {
		waitForElementToAppear(productBy);
		if(!isElementPresent(viewButton)) {
			System.out.println("View button not present in catalogue page");
		}
	
		viewButton.click();
	}
	public boolean verifyProductCardDetail(String productName, String price) {
		// if product empty then return false
				if (productCards.isEmpty()) {
					return false;
				}
		
		

		return true;
	}

	public void searchProductFromSearchBar(String productName) throws InterruptedException {
		// storing show result msg
		String msg1 = showResults.getText();
		// wait for searchbar to load
		waitForWebElementToAppear(productSearchBar);
		// enter product in searchbar
		productSearchBar.sendKeys(productName,Keys.ENTER);
		// Thread.sleep(3000);
		// hit enter for products to re occur based on search bar
		//productSearchBar.sendKeys(Keys.ENTER);
		// wait for show results msg to change from msg1 to other text
		waitForTextToChangeInElement(showResults, msg1);
		// Thread.sleep(3000);
	}

	public boolean verifyProductsAppearAfterSearchbar(String productName) {
		// if product empty then return false
				if (productCards.isEmpty()) {
					return false;
				}
		
		// used By locator for waits to avoid stale element execption due to dynmaic
		// elements
		waitForVisibilityOfAllElementsLocatedBy(productCardsBy);
		// waitForVisibilityOfAllListOfWebElements(productCards);
		System.out.println("size:" + productCards.size());
		
		// if product name matches with search list return true else false
		for (int i = 0; i < productCards.size(); i++) {
			String name = productNamesInCard.get(i).getText().trim();
			System.out.println("product name:" + name);
			if (!productName.equals(name)) {
				return false;
			}

		}

		return true;
	}

	public void enterPriceRange(String minPrice, String maxPrice) {
		String msg1 = showResults.getText();
		minPriceRange.sendKeys(minPrice);
		maxPriceRange.sendKeys(maxPrice);
		productSearchBar.sendKeys(Keys.ENTER);
		// wait for show results msg to change from msg1 to other text
		waitForTextToChangeInElement(showResults, msg1);

	}

	public boolean verifyProductsAppearBasedOnPriceRange(String minPrice, String maxPrice) {
		// used by locator for waits to avoid stale element execption due to dynmaic
		// elements
		waitForVisibilityOfAllElementsLocatedBy(productCardsBy);
		// waitForVisibilityOfAllListOfWebElements(productCards);
		System.out.println("size:" + productCards.size());
		int minRange=Integer.parseInt(minPrice);
		int maxRange=Integer.parseInt(maxPrice);
		// if product empty then return false
		if (productCards.isEmpty()) 
			return false;
			
		for(int i=0;i<productCards.size();i++) {
			//extracting price within the product card and not driver
			//ie using productCard.findElement(), instead of driver.findElement()
			String prodRate=productCards.get(i).findElement(productPriceBy).getText();
			System.out.println("rate in string form:"+prodRate);
			int rate=extractPriceFromText(prodRate);
			
			if(!(rate>=minRange && rate<=maxRange)) {
				return false;
		  }	
		}
		return true;
	}
	private int extractPriceFromText(String priceText) {
	    // "$ 11500" → remove $ and spaces → "11500" → parse to int
	    return Integer.parseInt(priceText.replace("$", "").trim());//$ 676=>676
	}
	//returns no. of items added to cart
	public int addProductToCart(String product) {
		
		waitForVisibilityOfAllElementsLocatedBy(productCardsBy);
		//get initial cart count
		System.out.println("product given:"+product);
		int itemAdded=0;
		boolean flag=false;
		if(productCards.isEmpty()) {
			System.out.println("no products in catalogue page");
		}
		//productCards
		for(int i=0;i<productNamesInCard.size();i++) {
			WebElement productEle=productNamesInCard.get(i);
			String getProduct=productEle.getText();
			System.out.println(getProduct);
			if(getProduct.equalsIgnoreCase(product)) {
				addToCartButtons.get(i).click();
				
				waitForElementToAppear(toastContainer);

				waitForElementToBeInvisible(spinner);
				itemAdded++;
				flag=true;
				//break;
				
			}
		}
		
		if(!flag) {
			System.out.println("product not present in catalogie and could not able to add to cart");
		}
		return itemAdded;
		
	}
	public int getCartCount() {
		String count= cartHeaderCount.getText().trim();
		//since when no item in car, in dom no number is shown
		int actualCount=count.isEmpty()? 0 : Integer.parseInt(count);
		System.out.println("count"+count);
		return actualCount;
	}
	

	public List<WebElement> getProductList() {
		waitForElementToAppear(productBy);
		return products;
	}

	public WebElement getProductByName(String prodName) {

		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(prodName)).findFirst()
				.orElse(null);

		return prod;
	}

	public void addProducttoCart(String prodName) {
		// note:we cannot write pagefactory for below css element as its
		// prod.findElement()
		// and not driver.findEleemnt()
		// thus use By addToCart=By.cssSelector(".card-body button:last-of-type");
		// instead of @FindBy

		// prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		WebElement prod = getProductByName(prodName);
		prod.findElement(addToCart).click();

		// checking green banner saying:product added to cart
		// Below we cant use pagefactory thus initialize using By,since its not driver
		// by
		// w.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		waitForElementToAppear(toastContainer);

		// for below used @findBy Page factory as it is driver.findElement
		// w.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		waitForElementToBeInvisible(spinner);

	}
public void AddProductToCart02(String product) {
		
		
		
	}

}
