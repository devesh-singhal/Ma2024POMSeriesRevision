package com.qa.opencart.pges;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By firstName = By.id("input-firstname");
	 private By lastName = By.id("input-lastname");
	 private By email = By.id("input-email");
	 private By telephone = By.id("input-telephone");
	 private By password = By.id("input-password");
	 private By confirmPassword = By.id("input-confirm");
	 private By susbcribeYes = By.xpath("(//label[@class='radio-inline'])[1]/input[@type='radio']");
	 private By susbcribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input[@type='radio']");
	 private By agreeCheckBox = By.xpath("//input[@type='checkbox']");
	 private By continueButton = By.xpath("//input[@class='btn btn-primary' and @type='submit']");
	 
	 private By logoutLink = By.linkText("Logout");
	 private By registerLink = By.linkText("Register");
	 private By successMessage =  By.cssSelector("div#content h1") ;
	
	

	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
		
	}

	public boolean userRegistration (String firstName, String lastName, String email, String telephone, String password, String subscribe) throws InterruptedException  {
		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(firstName);
	
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
	    eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
	
	
	 if (subscribe.equalsIgnoreCase ("yes") ) {
	     eleUtil.doClick(susbcribeYes);
		} else {
		 eleUtil.doClick(susbcribeNo);
		
		}
	   
	   eleUtil.doClick(agreeCheckBox);
	   eleUtil.doClick(continueButton);
	   
	   String successMsg = eleUtil.waitForElementVisible(successMessage, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
	   System.out.println(successMsg);
	   
	   if (successMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
		   eleUtil.doClick(logoutLink);
		   eleUtil.doClick(registerLink);
		   
		   return true;
	   } else {
		   return false;
	   }
	}
	
	
}








