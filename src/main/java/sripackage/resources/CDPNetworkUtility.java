package sripackage.resources;

import java.util.HashMap;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v122.network.Network;

public class CDPNetworkUtility {

	private WebDriver driver;
	HashMap<String,Integer> map=new HashMap<String,Integer>();
	
	public void CDPNetworkUtility(WebDriver driver) {
		this.driver = driver;
	}

	public void startNetworkMonitoring() {
		if(!(driver instanceof ChromeDriver)) {
			System.out.println("WARNING! CDP monitoring only supported on Chrome. Skipping.");
		}
		ChromeDriver chromeDriver=(ChromeDriver)driver;
		DevTools devTool=chromeDriver.getDevTools();
		devTool.createSession();
		devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTool.addListener(Network.responseReceived(), response->{
		String url=response.getResponse().getUrl();
		int statusCode=response.getResponse().getStatus();
		String mimeType=response.getResponse().getMimeType();
		if(mimeType!=null && mimeType.startsWith("image/")) {
			map.put(url, statusCode);
			System.out.println("[CDPNetworkUtility] Image: "
                    + url + " | HTTP: " + statusCode);
		}
		});
		 System.out.println("[CDPNetworkUtility] Network monitoring started.");
		 
	
	}

}
