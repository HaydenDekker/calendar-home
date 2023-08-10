package com.hdekker.calendarhome.sdk;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.TestProfiles;

import jakarta.annotation.PostConstruct;

@Profile(TestProfiles.INTEGRATION)
@Component
public class CalendarUISDK {
	
	@Autowired
	WebDriver webDriver;
	
	WebDriverWait wdw;
	
	public void openCalendar() {
		
		webDriver.get("https://localhost:8080/calendar");
		wdw.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#view-title")));
		
	}
	
	public String checkEventBySubjectExists(String subject) {
		
		By ceSel = By.cssSelector(".calendar-event");
		wdw.until(ExpectedConditions.presenceOfElementLocated(ceSel));
		
		List<WebElement> ces = webDriver.findElements(ceSel);
		
		Optional<WebElement> opt = ces.stream()
			.filter(we->we.getText().contains(subject))
			.findFirst();
			
		return opt.map(we->we.getText())
				.orElse("Could not get text with subject " + subject);
		
	}
	
	@PostConstruct
	public void init() {
		wdw = new WebDriverWait(webDriver, Duration.ofSeconds(10));
	}
	
}
