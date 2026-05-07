package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	
	private WebDriver driver;
	private Actions action;
	private JavaScriptUtil jsUtil;
	
	 public ElementUtil(WebDriver driver) {
		 this.driver = driver;
		 action = new Actions(driver);
		 jsUtil = new JavaScriptUtil(driver);
		 
		}

	 @Step("")
	 public void doClick(By locator) { 
		 getElement(locator).click();
		 
}
	 public void doClick(By locator, int timeOut) { 
		 waitForElementVisible(locator, timeOut).click();
		 
}
	@Step("ENTERING VALUE : {1} INTO THE LOCATOR : {0}")
    public void doSendKeys(By locator, String value) {
			getElement(locator).sendKeys(value);
			}
    
    public void doSendKeys(WebElement element, String value) {
 			element.clear();
 			element.sendKeys(value);
 			}
	 
    public void doSendKeys(By locator, String value, int timeOut) { 
    	 waitForElementVisible(locator, timeOut).click();
		
		 
}
	 
	 public void doSendKeys(By locator, CharSequence... value) {
		 getElement(locator).sendKeys(value);
		}
	 
      private void checkElementHighlight(WebElement element) {
    	  if (Boolean.parseBoolean(DriverFactory.isHighlight)) {
    		  jsUtil.flash(element);
    	  }
    	 
      }
	 
	 public  WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		checkElementHighlight(element);
		return element;
	 }
	 
	 
	 public String doGetElementGetAttribute (By locator, String attributeName ) {
			return getElement(locator).getAttribute(attributeName);
			 
		 }
	 
	 public int getElementsCount(By locator) {
			return getElements (locator).size();
			
	  }
		
		public boolean isElementNotPresent (By locator ){
			 if( getElementsCount (locator) == 0) {
				 return true;
			 }
			return false;
			}
		
	  public boolean isElementPresentMultipleTimes (By locator ) {
		  if (getElementsCount (locator) >=1) {
			  return true;
			   }
	        return false;
		  }
	  
	  
	  public boolean isElementPresent (By locator, int expectedElementCount) {
			if (getElementsCount (locator) == expectedElementCount) {
				return true;
			}
			 return false;
			}
				

		
		public boolean isElementPresent (By locator) {
			if (getElementsCount (locator) ==1) {
				return true;
			}
			 return false;
			}
				
	  
	  
		
		public  List <WebElement> getElements(By locator) {
			    return driver.findElements(locator);
		 }

		
		
		public void printElementTextList(By locator) {
			   List<String> eleTextList =   getElementTextList (locator) ;
				for (String e :eleTextList ) {
					System.out.println(e);
				}
			}
			
			public List<String> getElementTextList (By locator ) {
				List <WebElement >eleList = getElements(locator);
				 List <String> eleTextList = new ArrayList <String>();
				  for (WebElement e : eleList ) {
					String eleText = e.getText();
				  if (eleText.length()!=0) { eleTextList.add(eleText); }
					 
					
				}
		 		   return eleTextList;
			}
			
	 
			
			
			
			public boolean doSearch (By searchField,  By suggestions, String searchKey, String matchedValue) throws InterruptedException {
				 
				boolean flag = false;
				doSendKeys(searchField, searchKey);
				 Thread.sleep(3000);
				 
				 List<WebElement> suggList = getElements(suggestions);
				int totalSuggestions = suggList.size();
				System.out.println("total number of suggestions====== " + totalSuggestions);
				
				if (totalSuggestions ==0) {
					System.out.println("No suggestions found");
					throw new FrameworkException ("No Suggesstions found");
					
				}
				 

				 
				 for (WebElement e : suggList) {
					 String text = e.getText();
					  System.out.println(text);
					 Thread.sleep(5000);
			           if (text.contains(matchedValue)) {
			        	 e.click();
			        	 flag = true;
						 break;
						 
					 }
					 }
				 
				if (flag) {
					System.out.println(matchedValue + " is found...");
					return true;
				}
				else {
					System.out.println(matchedValue + " is NOT found...");
					
				}
				return false;
				 
		}
			
			 
	 public boolean isElementDisplayed(By locator) {
		  
		 try {
		 return getElement(locator).isDisplayed();
		 } catch (NoSuchElementException e) {
			 System.out.println("Element is not displayed:  " +locator);
			 return false;
		 }
		 }

	 
	 public String getElementText(By locator) {
		 String elementText = getElement(locator).getText();
		 if (elementText !=null) {
			 return elementText;
		 }
		 else {
			 System.out.println("element tet is null: " + elementText);
			 return null;
		 }

		 
	 }
	 
	 
//**********Select Drop Down Utilities**********************
	 
	 private Select getSelect(By locator) {
		  return new Select(getElement(locator));
	 }
	 
	  public int getDropDownOptionsCount (By locator) {
		     return getSelect(locator).getOptions().size();
		  	 }

		  
		  public void selectDropdownValueByVisibleText (By locator, String visibleText) {
			  getSelect(locator).selectByVisibleText(visibleText);
			}

		  public void selectDropdownValueByIndex (By locator, int index) {
			  getSelect(locator).selectByIndex(index);
			}
		  
		  public void selectDropdownValueByValue (By locator, String value) {
			  getSelect(locator).selectByValue(value);
			}
		 
		  
	//***************************************************************************
		    public List<String> getDropDownOptionsTextList (By locator) {
			    List<WebElement> optionsList = getSelect(locator).getOptions();
				System.out.println(optionsList.size());
				List<String> optionsTextList = new ArrayList<String>();
				    for (WebElement e : optionsList) {
					String text = e.getText();
					// System.out.println(text);
					optionsTextList.add(text);
				}
				return optionsTextList;
			}
		  
		  
		// *********select drop down value by using Select class::
		   public void selectDropDownValueUsingSelect(By locator, String value) {
			   List<WebElement> dropdownOptionList = getSelect(locator).getOptions();
			selectDropDown(dropdownOptionList, value);
		
	  }
		  
		  // *********select drop down value without using Select class::
			   public void selectDropDownValue(By locator, String value) {
				List<WebElement> dropdownOptionList = getElements(locator);
				selectDropDown(dropdownOptionList, value);
			
		  }
		  
		  private void selectDropDown(List<WebElement>dropdownOptionList , String value) {
			 System.out.println("total number of dropdown values...." +dropdownOptionList.size() );
				for(WebElement e : dropdownOptionList) {
					  String text = e.getText();
					  System.out.println(text);
					  if (text.equals(value)) {
					     e.click();
					     break;
         }
				}			
		  }
//***************ACTION UTILS********************************************
		  
		  @Step ("CLICKING ON ELEMENT USING LOCATOR : {0}")
		  public void doActionClick(By locator) {
				action.click(getElement(locator)).perform();
			}
			
			public  void doActionsSendKeys(By locator, String value) {
		     action.sendKeys(getElement(locator), value).perform();
			
			}
		  
		  /**
			 * This method is used for handling 2 level of parent and child menu on basis if By locator
			 * 
			 * @param parentMenu
			 * @param childMenu
			 * @throws InterruptedException
			 */
		
		  
		  
					 
	public  void parentChildMenuAction(String parentMenu, String childMenu) throws InterruptedException {
		By addons = By.xpath("//div[text()='"+parentMenu+"']");
		 By visaServices = By.xpath("//div[text()='"+childMenu+"']");
							 
		 action.moveToElement(getElement(addons)).perform();
				 Thread.sleep(1500);
		doClick(visaServices);
							 
 }
	
	
	
	/**
	 * This method is used for handling Multi- level (4) of parent and child menu on basis if By locator
	 * 
	 * @param level1
	 * @param level2
	 * @param level3
	 * @param level4
	 * @throws InterruptedException
	 */	
	
	public void parentChildMenuMultiHandle(By level1, By level2, By level3, By level4) throws InterruptedException {
		 doClick(level1);
		 Thread.sleep(2000);
		 action.moveToElement(getElement(level2)).perform();
		 Thread.sleep(1000);
		 action.moveToElement(getElement(level3)).perform();
		 Thread.sleep(1000);
		 doClick(level4);
		 
		 
}
					  
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	 
	 
	public  WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
		
	}
	 
	@Step("WAITING FOR WEBELEMENT USING LOCATOR : {0} within the  TIMEOUT: {1}") 
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	 
	public  WebElement waitForElementVisibleWithFluentFeatures(By locator, int timeOut, int pollingTime) {
	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			                .withTimeout(Duration.ofSeconds(timeOut))
			                .pollingEvery(Duration.ofSeconds(pollingTime))
			                .ignoring(NoSuchElementException.class)
			                .ignoring(StaleElementReferenceException.class)
			                .ignoring(ElementNotInteractableException.class)
			                .withMessage("Element not found=======" + locator);
	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	
	
	
	}
		
	 
	
	
	
	 
	public void waitForElementAndClick(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	 
	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		
}
	
	
	
	public List<WebElement> waitForElementsPresence(By locator, int timeOut){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		
		}		
	
	public String getPagTitleIs(String expectedTitle, int timeOut) {
		if (waitForTitleIs(expectedTitle, timeOut)) {
			return driver.getTitle();
		
	}   else {
		return "-1";
	}	
	}
	
	
	
	public  String getPagTitleContains(String expectedTitle, int timeOut) {
		if (waitForTitleContains(expectedTitle, timeOut)) {
			return driver.getTitle();
		
	}   else {
		return "-1";
	}	
	}
	
	
	   public boolean waitForTitleIs(String expectedTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		boolean flag = false;
		
		try {
		return wait.until(ExpectedConditions.titleIs(expectedTitle));	
		 }  
		catch(org.openqa.selenium.TimeoutException e) {
			System.out.println("title not matched");
			return flag;
		}
	}
	
	
	   
	   public boolean waitForTitleContains(String expectedTitle, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			boolean flag = false;
			
			try {
			return wait.until(ExpectedConditions.titleContains(expectedTitle));	
			 
		}  
			catch(org.openqa.selenium.TimeoutException e) {
				System.out.println("title not matched");
				return flag;
			}
		}
	   
	   
	   public String waitForTitleContainsAndReturn(String fractionTitle, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			 wait.until(ExpectedConditions.titleContains(fractionTitle));	
			 return driver.getTitle();
			 
		}  
			catch(org.openqa.selenium.TimeoutException e) {
				System.out.println("title not matched");
				return "-1";
			}
		}
	   
	   
	   
	   public String getPagURLContains(String fractionURL, int timeOut) {
			if (waitForURLContains(fractionURL, timeOut)) {
			return driver.getCurrentUrl();
			
		}   else {
			return "-1";
		}	
		}
		
	   
	   public boolean waitForURLContains(String fractionURL, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			boolean flag = false;
			
			try {
			return wait.until(ExpectedConditions.urlContains(fractionURL));	
			 
			 
			}  
			catch(org.openqa.selenium.TimeoutException e) {
				System.out.println("URL is not matched");
				return flag;
			}
		}
	   
	   
	   public String  waitForURLContainsAndReturn(String fractionURL, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			 wait.until(ExpectedConditions.urlContains(fractionURL));	
			 return driver.getCurrentUrl();
			 
			 }  
			catch(org.openqa.selenium.TimeoutException e) {
				System.out.println("URL is not matched");
				return "-1";
			}
		}
	   
	   
	   
	   public Alert waitForAlertAndSwitch(int timeOut) {
		   WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(timeOut));
		   return wait.until(ExpectedConditions.alertIsPresent());
		 }
	   
	   public Alert waitForAlertUsingFluentWaitAndSwitch(int timeOut) {
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		                           .withTimeout(Duration.ofSeconds(timeOut))
		                           .ignoring(NoAlertPresentException.class)
		                           .withMessage("=== JS alert is not present=======");
		   return wait.until(ExpectedConditions.alertIsPresent());
		                           
	   }
	   
	   public String getAlertText(int timeOut) {
		   return waitForAlertAndSwitch(timeOut).getText();
	   }
	   

	   public void acceptAlert(int timeOut) {
		    waitForAlertAndSwitch(timeOut).accept();
	   }
	   
	   
	   public void dismissAlert(int timeOut) {
		    waitForAlertAndSwitch(timeOut).dismiss();
	   }
	   
	   public void enterValueOnAlert(int timeOut, String value) {
		    waitForAlertAndSwitch(timeOut).sendKeys(value);
		    
	   }
	   
	  //******** WAIT FOR FRAME UTILS*************
	   
	   public void waitForFrameUsingLocatorAndSwitchToIt(By framelocator, int timeOut) {
		   WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framelocator));
		   
	   }
	   
	   public void waitForFrameUsingLocatorAndSwitchToIt(int frameIndex, int timeOut) {
		   WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		   
	   }
	   
	   
	   public void waitForFrameUsingLocatorAndSwitchToIt(String idOrName, int timeOut) {
		   WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
		   
	   }
	   
	   
	   public void waitForFrameUsingLocatorAndSwitchToIt(WebElement frameElement, int timeOut) {
		   WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		   
	   }
	   
	   //****** wait for window/Tab*********
	   
  public boolean waitForNewWindowOrTab(int expectedNumberOfWindows, int timeOut) {
	  WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds(timeOut));
	  
	  try {
		 if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
			 System.out.println("windows are matched");
			 return true;
			 
		 }
	  
  } catch (org.openqa.selenium.TimeoutException e ) {
	  System.out.println("Number of windows are not matched");
  }
	  return false;
	   
	
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
  }	   
	   
	   
}


















