package com.hdekker.calendarhome.views.calendar;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("calendar")
public class CalendarUI extends VerticalLayout{

	public CalendarUI() {
		
		H2 title = new H2("Calendar"); 
		add(title);
		title.setId("view-title");
		
	}
	
}
