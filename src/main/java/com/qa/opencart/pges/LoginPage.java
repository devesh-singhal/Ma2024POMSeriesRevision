package com.qa.opencart.pges;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	public WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private by locators::
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By.cssSelector("img.img-responsive");
	private By registerLink = By.linkText("Register");
	
	//2. public page constructors::
	
	public LoginPage (WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}

	
	
	
	//3. public page actions/methods::
	
	@Step("Getting login page title value")
	public String getLoginPageTitle() {
	String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
	System.out.println("login page title is...."+ title);
		return title;
	}	
		
	@Step("Getting login page URL value")
	public String getLoginPageURL() {
	  String url = eleUtil.waitForURLContainsAndReturn(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_TIME_OUT);
	  System.out.println("login page URL is "+url);
	  return url;
	  
  }
  
	@Step("CHECKING IS FORGOT PASSWORD LINK EXISTS") 
  public boolean isForgotPasswordLinkExist() {
	return  eleUtil.isElementDisplayed(forgotPwdLink);
		
}
	@Step("CHECKING LOGO EXISTS ON LOGIN PAGE") 
  public boolean isLogoExist() {
	  return  eleUtil.isElementDisplayed(logo);
		
		
}
	
@Step("LOGIN WITH USERNAME : {0} and PASSWORD : {1}") 
  public AccountsPage doLogin(String userName, String pwd) {
	  System.out.println("Login credentials are===>" + userName + "::" + pwd);
	  eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(userName);
	  eleUtil.doSendKeys(password, pwd);
	  eleUtil.doActionClick(loginBtn);
	   return new AccountsPage(driver);
  }
	
	@Step("NAVIGATING TO REGISTER PAGE")
  public RegisterPage navigateToRegister() {
	    eleUtil.doClick(registerLink);
	    return new RegisterPage(driver);
  }
	
	
	
	
	
	
}



