package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

import io.qameta.allure.Step;



public class DriverFactory {

	static WebDriver driver;
	public Properties prop;
    public static String isHighlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();	
	
	
	
	@Step("INITIALIZING THE DRIVER WITH PROPERTIES : {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("browser name is ..."+browserName);
		isHighlight = prop.getProperty("highlight");
		OptionsManager om = new OptionsManager(prop);
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver(om.getChromeOptions());
			tlDriver.set(new ChromeDriver(om.getChromeOptions()));
		    break;

		case "firefox":
			//driver = new FirefoxDriver(om.getfirefoxChromeOptions());
			tlDriver.set(new FirefoxDriver(om.getfirefoxChromeOptions()));
	     	break;

		case "edge":
			driver = new EdgeDriver();
	     	break;

		case "safari":
			driver = new SafariDriver();
	     	break;

		default:
			System.out.println("please pass the right browsername" +browserName);
			throw new BrowserException("INVALID BROWSER" + browserName);
			
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
		
}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
		
	}
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running testcases on environment:" + envName);
		
		try {
		if (envName==null) {
			System.out.println("Envrironment is null hence running testcases on by default QA env");
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		} else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "uat":
				ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;
				
			default:
				System.out.println("PLEASE PASS THE RIGHT ENV NAME " +envName);
				throw new FrameworkException("INVALID ENVIRONMENT NAME");
				
			}
		}
		
		prop.load(ip);
		
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()+ ".png";
		File destination = new File(path);
		
		try {
			org.openqa.selenium.io.FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
		  e.printStackTrace();
		}
		
		return path;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}


