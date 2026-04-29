package srishtiPakage.TestComponents;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;

import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.ProductCataloguePage;

//not used yet->might delete later
public class AuthenticatedBaseTest extends BaseTest {

	//  available to all test classes that extend this
    public ProductCataloguePage productCataloguePage;

    //  @BeforeMethod lives here — not in any test class
    @BeforeMethod(alwaysRun = true)
    public void launchAndLogin() throws IOException {
        driver      = initializeDriver();
        landingPage = new LandingPage(driver);
        siteUrl     = getAppUrl();
        landingPage.goTo(siteUrl);
        productCataloguePage = landingPage.loginApplication(prop.getProperty("userEmail"),prop.getProperty("userPassword") );
    }
}
