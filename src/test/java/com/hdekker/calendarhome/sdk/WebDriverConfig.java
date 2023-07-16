package com.hdekker.calendarhome.sdk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bonigarcia.wdm.WebDriverManager;

@Configuration
public class WebDriverConfig {
	
	@Bean
	public WebDriver getWebDriver(){
		
		 WebDriverManager.chromedriver()
		 		.setup();
		 
		 ChromeOptions options = new ChromeOptions();
			options.setAcceptInsecureCerts(true);
		 
		 return new ChromeDriver(options);
		
	}
	
}
