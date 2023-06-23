package com.hdekker.calendarhome.views.subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.calendarhome.outlook.AuthValidation;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("subscribe")
public class Subscribe extends VerticalLayout {

	Logger log = LoggerFactory.getLogger(Subscribe.class);
	
	@Autowired
	AuthValidation authValidation;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5388571066401588446L;

	public Subscribe() {
		
		Button subscribe = new Button("subscribe");
		add(subscribe);
		
		subscribe.addClickListener(s-> {
			
			
			String redirectURL = authValidation.getAuthRedirectURL();
			log.info("Subscribing to calendar.. " + redirectURL);
			UI.getCurrent()
				.getPage().setLocation(redirectURL);
			
		});
		
	}
	
}
