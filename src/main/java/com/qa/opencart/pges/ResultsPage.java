package com.qa.opencart.pges;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By searchHeader = By.cssSelector("div#content h2");
	private By results = By.cssSelector("div.product-thumb");
	
	public ResultsPage(WebDriver driver) {
	this.driver= driver;
	 eleUtil = new ElementUtil(driver);
	
	}
	
	public String getSearcHeader() {
String searchHeaderValue = eleUtil.waitForElementVisible(searchHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
        return searchHeaderValue;
}
	
	public int getSearchResultsCount() {
		int resultsCount = eleUtil.waitForElementsVisible(results, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("search results count =" +resultsCount);
		return resultsCount;
		
	}
	
	public ProductInfoPage selectProduct(String productName){
	System.out.println("selecting the product ::   " + productName);
	 eleUtil.doClick(By.linkText(productName));
	 return new ProductInfoPage(driver);
		
		
		
	}
	}



















