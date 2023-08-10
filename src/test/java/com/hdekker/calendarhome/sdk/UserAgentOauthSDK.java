package com.hdekker.calendarhome.sdk;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationService;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Component
@Profile(TestProfiles.INTEGRATION)
public class UserAgentOauthSDK {

	@Autowired
	WebDriver webDriver;
	
	Logger log = LoggerFactory.getLogger(UserAgentOauthSDK.class);
	
	public static Integer TIME_OUT = 10;
	
	WebDriverWait wdw;
	
	Authentication authentication;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Value("${test.microsoft.user}")
	public String microSoftAccount;
	
	@Value("${test.microsoft.password}")
	public String microsoftAccountPassword;

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public Authentication loginUser() {
		
		log.info("Using account " + microSoftAccount);
		
		Mono<Authentication> auth =  Mono.create(s->{
			
			authenticationService.listenForUserAuthentication(a->{
				log.info("Authentication successful " + a.accessToken());
				s.success(a);
			});
			
			openSubscribe();
			clickSubscribe();
			enterUserEmailAndPasswordToMicrosoftOauth(microSoftAccount, microsoftAccountPassword);
			
		});
		Authentication res = auth.timeout(Duration.ofSeconds(20))
					.block();
		return res;
	}
	
	@PostConstruct
	public void init() {
		wdw = new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT));
	}
	
	public void openSubscribe() {
		
		assertThat(webDriver).isNotNull();
		
		webDriver.get("https://localhost:8080/subscribe");
		
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#subscribe")));
		
		
	}
	
	public void quit() {
		
		try {
			webDriver.quit();
		}catch(Exception e) {
			
		}
	
	}
	
	private void clickSubscribe() {
		
		webDriver.findElement(By.cssSelector("#subscribe")).click();
		
	}
	
	public String getInputTagWithTypeAttributeValue(String typeAttributeValue) {
		return "input[type='" + typeAttributeValue + "']";
	}
	
	public String getInputTagWithValueAttributeValue(String typeAttributeValue) {
		return "input[value='" + typeAttributeValue + "']";
	}
	
	private void optionallyAgreeToPermissions() {
		
		try {
			wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithValueAttributeValue("Accept"))));
			webDriver.findElement(By.cssSelector(getInputTagWithValueAttributeValue("Accept")))
				.click();
			
		}catch(Exception e) {
			log.info("Must have already agreed to permissions.");
		
		}
		
	}
	
	private void enterUserEmailAndPasswordToMicrosoftOauth(String microSoftAccount, CharSequence microsoftAccountPassword) {
		
		
		By emailSelector = By.cssSelector(getInputTagWithTypeAttributeValue("email"));
		wdw.until(ExpectedConditions.elementToBeClickable(emailSelector));
		 
		webDriver.findElement(emailSelector).sendKeys(microSoftAccount);
		webDriver.findElement(By.cssSelector(getInputTagWithTypeAttributeValue("submit")))
			.click();
		
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithTypeAttributeValue("password"))));
		By passwordSelector = By.cssSelector(getInputTagWithTypeAttributeValue("password"));
		webDriver.findElement(passwordSelector).sendKeys(microsoftAccountPassword);
		
		//submit
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithTypeAttributeValue("submit"))));
		webDriver.findElement(By.cssSelector(getInputTagWithTypeAttributeValue("submit")))
			.click();
		
		wdw.until(ExpectedConditions.elementToBeClickable(By.cssSelector(getInputTagWithTypeAttributeValue("button"))));
		webDriver.findElement(By.cssSelector(getInputTagWithTypeAttributeValue("button")))
			.click();
		
		optionallyAgreeToPermissions();
		
		
	}
	
	
}
