package com.qa.opencart.pges;

import org.openqa.selenium.By;

public class TestPage {
	
	By loc = By.cssSelector(".demo");
	public int getPage() {
		System.out.println("click on demo");
		return 0;
	}

}
