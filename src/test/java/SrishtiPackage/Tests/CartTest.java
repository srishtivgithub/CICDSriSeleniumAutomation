package SrishtiPackage.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import SrishtiPackage.TestComponents.BaseTest;
import sripackage.AbstractComponents.AbstractComponents;
import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.ProductCataloguePage;

public class CartTest extends BaseTest{
	ProductCataloguePage productCataloguePage;
	CartPage cartPage;
//verify productname, amount is same
	@Test(priority=1)
	public void verifyProductDetailsInCart() {
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.addProductToCart("ZARA COAT 3");
		cartPage=productCataloguePage.goToCartPage();
		Assert.assertFalse(cartPage.verifyProductinCartPage("ZARA COAT 3"),"Product name mistmatch or No products in Cart");
		
	}
	//test red color css of delete button as well
	@Test(priority=2,enabled=false)
	public void verifyDeleteProductFromCart() throws InterruptedException {
		landingPage=new LandingPage(driver);
		landingPage=new LandingPage(driver);
		productCataloguePage=landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		productCataloguePage.addProductToCart("ZARA COAT 3");
		cartPage=productCataloguePage.goToCartPage();
		cartPage.deleteItemInCart("ZARA COAT 3");
		Assert.assertTrue(cartPage.isProductDeletedFromCart("ZARA COAT 3"),"Product not deleted from cart");
		
	}
	@Test(priority=3,enabled=false)
	public void verifyBuyNowButtonNavigation() {
		
	}
	//2 product sum=subtotal and total
	@Test(priority=4,enabled=false)
	public void verifySubTotalLogic() {
		
	}
	@Test(priority=5,enabled=false)
	public void verifyCheckoutButtonNavigation() {
		
	}
	
}
