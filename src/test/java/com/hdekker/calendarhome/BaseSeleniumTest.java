package com.hdekker.calendarhome;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSeleniumTest {

	@Autowired
	WebDriver cd;
	
	@Test
	public void canOpenSubscribeUI() throws InterruptedException {
		
		assertThat(cd).isNotNull();
		
		cd.get("https://localhost:8080/subscribe");
		
		WebDriverWait wdw = new WebDriverWait(cd, Duration.ofSeconds(5));
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#subscribe")));
		
		try {
			
			cd.quit();
		}catch(Exception e) {
			
		}
		
		Thread.sleep(10);
		
		
	}
	
}
