package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pges.AccountsPage;
import com.qa.opencart.pges.LoginPage;
import com.qa.opencart.pges.ProductInfoPage;
import com.qa.opencart.pges.RegisterPage;
import com.qa.opencart.pges.ResultsPage;

public class BaseTest {
	
	DriverFactory df ;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	protected SoftAssert softAssert;
	
	
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional("chrome")String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if (browserName!=null) {
			prop.setProperty("browser", browserName);
		}
		
	    driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
		
   }

	
	 @AfterTest
	 public void tearDown() {
		 driver.quit();		 
		 
	 }
}
