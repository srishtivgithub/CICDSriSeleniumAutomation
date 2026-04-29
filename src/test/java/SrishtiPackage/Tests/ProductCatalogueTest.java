package SrishtiPackage.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import sripackage.AbstractComponents.AbstractComponents;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.ProductCataloguePage;
import sripackage.pageobjects.ViewPage;
import srishtiPakage.TestComponents.AuthenticatedBaseTest;
import srishtiPakage.TestComponents.BaseTest;

public class ProductCatalogueTest extends BaseTest {

	LandingPage landingPage;
	ProductCataloguePage productCataloguePage;
	AbstractComponents ac;
	////PC_TC_001
	@Test(priority=1,enabled=false)
	public void verifyProductCatalogueLoadsAfterSuccessfulLogin() {
		landingPage=new LandingPage(driver);
		ac=new AbstractComponents(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		Assert.assertTrue(ac.isUserLoggedIn(), "Login was not successful and Signout header not visible");
		Assert.assertTrue(productCataloguePage.isProductCataloguePageOpen(), "Product Page is not opened");
	}
	//PC_TC_004
	@Test(priority=2)
	public void verifySearchBarTofilterProducts() throws InterruptedException {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.searchProductFromSearchBar("ADIDAS ORIGINAL");
		Boolean flag=productCataloguePage.verifyProductsAppearAfterSearchbar("ADIDAS ORIGINAL");
		Assert.assertTrue(flag, "No product match or product name mismatch: searchbar not working");
		
	}
	//PC_TC_007
	@Test(priority=3,enabled=false)
	public void verifyfilterProductsByPriceRange() throws InterruptedException {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.enterPriceRange("10000", "20000");
		Boolean flag=productCataloguePage.verifyProductsAppearBasedOnPriceRange("10000", "20000");
		Assert.assertTrue(flag, "Products not in range: Price Range filter is not working");
		
	}
	//PC_TC_010
	@Test(priority=4,enabled=false)
	public void verifyCountUpdatesWhenProductAddedToCart () {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		int prevCartCount=productCataloguePage.getCartCount();
		int itemsAdded=productCataloguePage.addProductToCart("ZARA COAT 3");
		int expectedCartCount=productCataloguePage.getCartCount();
		Assert.assertEquals(expectedCartCount, prevCartCount+itemsAdded);
		
	
      }

	//PC_TC_003
	@Test(priority=5,enabled=false)
	public void verifyProductCardDisplaysDetails() throws InterruptedException {
		String productName="ADIDAS ORIGINAL";
		String price="$ 11500";
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.searchProductFromSearchBar(productName);
		
		Assert.assertEquals(productCataloguePage.getProductName(), productName);
		Assert.assertEquals(productCataloguePage.getProductPriceWithCurrency(), price);
		
	}
	
	//PC_TC_011
	@Test(priority=6,enabled=false)
	public void verifyViewNavigationFromProductCard() {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		ViewPage viewPage=new ViewPage(driver);
		String productInCatalogue=productCataloguePage.getProductName();
		int priceInCatalogue=productCataloguePage.getProductPrice();
		
		productCataloguePage.clickOnView();
		String productInView=viewPage.getProductNameInViewPage();
		int priceInView=viewPage.getProductPriceInViewPage();
		Assert.assertEquals(productInCatalogue, productInView);
		Assert.assertEquals(priceInCatalogue, priceInView);
	}
	
	//PC_TC_013
	@Test(priority=7,enabled=false)
	public void verifySearchBarWithInvalidKeyword() throws InterruptedException {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.searchProductFromSearchBar("xyz123notexist");
		Boolean flag=productCataloguePage.verifyProductsAppearAfterSearchbar("xyz123notexist");
		Assert.assertFalse(flag, " Search not working | Product match found for invalid product");
		Assert.assertFalse(productCataloguePage.showResultCount(), "Search not working | Product match found for invalid product");
	}
	
	
	//PC_TC_019
	@Test(priority=8,enabled=false)
	public void verifyHomeNavigationStaysOnCataloguePage() {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.clickHomeTab();
		Assert.assertTrue(productCataloguePage.isProductCataloguePageOpen(), "Product Page is not opened");
		
	}
	
	
	//PC_TC_020
	@Test(priority=9,enabled=false)
	public void verifyProductImageLoadCorrectlyForAllProductCard() {
		
	}
	
	
	//PC_TC_022
	@Test(priority=10,enabled=false)
	public void verifySideBarFilterVisibleOnProductCataloguePage() {
		
	}
	
}
