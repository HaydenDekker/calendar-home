package com.hdekker.calendarhome.sdk;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAgentSDK {

	@Autowired
	WebDriver webDriver;
	
	public static Integer TIME_OUT = 10;
	
	public void openSubscribe() {
		
		assertThat(webDriver).isNotNull();
		
		webDriver.get("https://localhost:8080/subscribe");
		
		WebDriverWait wdw = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#subscribe")));
		
		
	}
	
	public void quit() {
		
		try {
			webDriver.quit();
		}catch(Exception e) {
			
		}
	
	}
	
	public void clickSubscribe() {
		
		webDriver.findElement(By.cssSelector("#subscribe")).click();
		
	}
	
	public String getInputTagWithTypeAttributeValue(String typeAttributeValue) {
		return "input[type='" + typeAttributeValue + "']";
	}
	
	public String getInputTagWithValueAttributeValue(String typeAttributeValue) {
		return "input[value='" + typeAttributeValue + "']";
	}
	
	public void enterUserEmailAndPasswordToMicrosoftOauth(String microSoftAccount, CharSequence microsoftAccountPassword) {
		
		WebDriverWait wdw = new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT));
	
		By emailSelector = By.cssSelector(getInputTagWithTypeAttributeValue("email"));
		wdw.until(ExpectedConditions.elementToBeClickable(emailSelector));
		 
		webDriver.findElement(emailSelector).sendKeys(microSoftAccount);
		webDriver.findElement(By.cssSelector(getInputTagWithTypeAttributeValue("submit")))
			.click();
		
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithTypeAttributeValue("password"))));
		By passwordSelector = By.cssSelector(getInputTagWithTypeAttributeValue("password"));
		webDriver.findElement(passwordSelector).sendKeys(microsoftAccountPassword);
		
		//submit
		webDriver.findElement(By.cssSelector(getInputTagWithTypeAttributeValue("submit")))
			.click();
		
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithTypeAttributeValue("button"))));
		webDriver.findElement(By.cssSelector(getInputTagWithTypeAttributeValue("button")))
			.click();
		
		// Then allow access to the information.
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithValueAttributeValue("Accept"))));
		webDriver.findElement(By.cssSelector(getInputTagWithValueAttributeValue("Accept")))
			.click();
		
		
	}
	
	
}
