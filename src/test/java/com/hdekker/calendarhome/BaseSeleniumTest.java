package com.hdekker.calendarhome;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.hdekker.calendarhome.sdk.UserAgentSDK;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSeleniumTest {

	@Autowired
	UserAgentSDK userAgentSDK;
	
	@Test
	public void canOpenSubscribeUI() throws InterruptedException{
		
		userAgentSDK.openSubscribe();
		userAgentSDK.quit();
		
		Thread.sleep(100);
		
	}
	
}
