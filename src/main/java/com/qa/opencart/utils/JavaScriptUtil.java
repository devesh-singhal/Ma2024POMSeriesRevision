	package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	 
	public JavaScriptUtil(WebDriver driver) {
		this.driver=driver;
		js = (JavascriptExecutor)driver;
		
	}

	
	public String getPageTitle() {
	 return js.executeScript("return document.title;").toString();
	 
	}
	

	public String getPageURL() {
	 return js.executeScript("return document.URL;").toString();
	 
	}
	
	
	public void generateJsAlert(String mesg) {
		js.executeScript("alert('"+mesg+"')");
	}
	
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
		
	}
	
	public void goBackWithJS() {
	 js.executeScript("history.go(-1)");	
	}
	
	public void goForwardWithJS() {
		 js.executeScript("history.go(1)");	
		}
	
	
	
	public void pageRefreshWithJS() {
		 js.executeScript("history.go(0)");	
		}
	
	
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='10px solid GREEN'", element);
	}
	
	public void flash (WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i =0; i<10; i++) {
			changeColor ("rgb(0,200,0)", element);
			changeColor (bgcolor, element);
			
		}
		
		
	}
	
	private void changeColor (String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = ' "+color+" '", element);
	}
	
	
	
	
	
	
	
	
	
	
}
