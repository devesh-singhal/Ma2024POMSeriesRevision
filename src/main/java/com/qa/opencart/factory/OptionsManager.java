package com.qa.opencart.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
    private FirefoxOptions fo;
    
	public OptionsManager(Properties prop) {
		this.prop= prop;
	}
	
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		co.addArguments("--headless=new"); // Runs without opening a UI window
		co.addArguments("--no-sandbox");   // Bypasses OS security model constraints
		co.addArguments("--disable-dev-shm-usage"); // Prevents resource memory crashes
		
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
		}
		
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			
			
			co.setCapability("browserName", "chrome");
			co.setCapability("selenoid:options", Map.of("enableVNC", true));
			co.setBrowserVersion(prop.getProperty("browserversion").trim());
			
			Map<String, Object> selenoidOptions = new HashMap<>();
			selenoidOptions.put("screenResolution", "1280x1024x24");
			   selenoidOptions.put("enableVNC", true);
		selenoidOptions.put("name", prop.getProperty("testname"));
			co.setCapability("selenoid:options", selenoidOptions );
	
		}
	      return co;
}
	
	
	
	public FirefoxOptions getfirefoxChromeOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			fo.addArguments("--incognito");
		}
	      return fo;
}


	public Object chromedriver() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
