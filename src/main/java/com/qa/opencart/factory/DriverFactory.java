package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;


public class DriverFactory {

	WebDriver driver;
	public static String isHighlight;
	OptionsManager om;
	 Properties prop;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("browser name is ..."+browserName);

      om = new OptionsManager(prop);
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver(browserName);
			}

			else {
				// run TCS's on local machine / browser
				tlDriver.set(new ChromeDriver(om.getChromeOptions()));
			}

			break;

		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run TC'S on remote machine inside container
				init_remoteDriver("firefox");
			}

			else {
				// run TCS's on local machine / browser
				driver = new FirefoxDriver(om.getfirefoxChromeOptions());
			}

			break;

		case "edge":
			driver = new EdgeDriver();
			break;

		default:
			System.out.println(AppError.INVALID_BROWSER_MESG + browserName);
			throw new BrowserException(AppError.INVALID_BROWSER_MESG);

		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	private void init_remoteDriver(String browserName ) throws BrowserException  {
		System.out.println("Running test cases on grid with browser ...." + browserName);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			
			
			try {
				//String huburl = "http://13.206.243.95/:4444/wd/hub";
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), om.getChromeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "firefox":
			try {
				//String huburl = "http://13.206.243.95/:4444/wd/hub";
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), om.getfirefoxChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;

		default:
			System.out.println("please pass the right remote browser name");
			throw new BrowserException(AppError.INVALID_BROWSER_MESG);
		}
		return;

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


