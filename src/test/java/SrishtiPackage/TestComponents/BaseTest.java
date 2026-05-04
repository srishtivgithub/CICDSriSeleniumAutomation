package SrishtiPackage.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import sripackage.pageobjects.LandingPage;
import sripackage.resources.ConfigReaderProperties;

public class BaseTest {
	
	public WebDriver driver;
	public ChromeOptions options;
	public LandingPage landingPage;
	public Properties prop;
	public String siteUrl;
	
	@SuppressWarnings("deprecation")
	public WebDriver initializeDriver() throws IOException {
		/*
		//properties class
		
	    prop=new Properties();
		
		//as load() takes Input stream as argument,thus converting GlobalData.properties file
		//into Input stream using FileInputStream class object= fis
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\sripackage\\resources\\GlobalData.properties");
		
		//loading GlobalData.properties file using load()
		//load() method automatically parse key-value pair from GlobalData.properties file
		//and extract all key-value pair from GlobalData.properties file
		prop.load(fis); //properties object=prop is reading GlobalData.properties file
		
		
		//getting "browser" property value from GlobalData.properties file
		//String browserName=prop.getProperty("browser");//line 1
		*/
		
		// ✅ delegate to ConfigReader — no manual loading
	    prop = ConfigReaderProperties.getProperties();
		
		//as we may get global data from maven cmd also so instead of line 1 write below
		//if we get browser value from maven then it will be preferred else GlobalData.properties file
		String browserName=System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		if(browserName.contains("chrome")){
			
			// zooming out the browser-to make all elements visible
			options = new ChromeOptions();
			options.addArguments("force-device-scale-factor=0.75");
			options.addArguments("high-dpi-support=0.75");

			WebDriverManager.chromedriver().setup();
			
			if(browserName.contains("headless")) {
				options.addArguments("headless");
			}
		    driver = new ChromeDriver(options);
		    //driver.manage().window().setSize(new Dimension(1440, 900));//full screen
			
		}
		
		else if (browserName.equalsIgnoreCase("edge")) {
			//edge 
		}
		
		else if (browserName.equalsIgnoreCase("firefox")) {
			//firefox 
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}
	public String getAppUrl() {
		
		siteUrl=System.getProperty("url")!=null ? System.getProperty("url") : prop.getProperty("url");
		
		return siteUrl;
	}
	@BeforeMethod(alwaysRun=true)//it will run always even though grouping applied
	public LandingPage launchApplication() throws IOException {
		driver=initializeDriver();
		landingPage=new LandingPage(driver);
	    siteUrl=getAppUrl();
		landingPage.goTo(siteUrl);
		return landingPage;
	}

	@AfterMethod(alwaysRun=true)//it will run always even though grouping applied
	public void tearDown() {
		
		driver.quit();
	}
	
	public String getScreenshot(String testcaseName, WebDriver driver) throws IOException {
		TakesScreenshot ss=(TakesScreenshot)driver;
		File source=ss.getScreenshotAs(OutputType.FILE);
		//File file=new File(System.getProperty("user.dir")+"\\reports\\" +testcaseName+ ".png");
		File destinationFile=new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator 
				+ testcaseName + ".png");
		destinationFile.getParentFile().mkdir();
		FileUtils.copyFile(source, destinationFile);
		//return System.getProperty("user.dir")+"\\reports\\" +testcaseName+ ".png";
		//return destinationFile.toString();
		//used below instead of above as image was broken in extentreport due to filepath
		return testcaseName + ".png";
	}

	

}
