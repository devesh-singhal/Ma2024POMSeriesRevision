package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.ExtentReportListener;

//@Listeners({ExtentReportListener.class, AnnotationTransformer.class})
public class LoginPageTest extends BaseTest {
	

    
	@Test
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
		
	}
	
	@Test
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
		
	}
	
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
	}
	
	@Test
	public void logoExiststTest() {
		Assert.assertTrue(loginPage.isLogoExist());
		
	}
	
	@Test (priority= Integer.MAX_VALUE)
	public void loginTest() {
		accPage	 = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE);
	}
	
	
	
	
	
	
	
	
	
	
	
}
