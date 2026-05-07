package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegister();
	}

	public String getRandomEmail() {
		return "apiautmation"+System.currentTimeMillis()+"@gmail.com";
	}
	
	
	@DataProvider
	public Object[][] getRegData() {
	 return ExcelUtil.getTestData(AppConstants.REG_SHEET_NAME);	
	}
	
	
	
	@Test (dataProvider = "getRegData")
	public void userRegisterTest(String firstname, String lastname, String telephone, String password, String subscribe) throws InterruptedException  {
		Assert.assertTrue(registerPage.userRegistration(firstname, lastname, getRandomEmail(), telephone, password, subscribe));
		
		}
	
	
	
}









