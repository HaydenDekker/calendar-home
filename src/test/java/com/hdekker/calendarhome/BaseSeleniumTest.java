package com.hdekker.calendarhome;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.hdekker.calendarhome.sdk.UserAgentOauthSDK;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSeleniumTest {

	UserAgentOauthSDK userAgentSDK;
	
	@Test
	public void canOpenSubscribeUI() throws InterruptedException{
		
		userAgentSDK.openSubscribe();
		userAgentSDK.quit();
		
		Thread.sleep(100);
		
	}
	
}
