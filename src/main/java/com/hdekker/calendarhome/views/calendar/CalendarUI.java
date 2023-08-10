package com.hdekker.calendarhome.views.calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.calendarhome.outlook.CalendarEventStream;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

@Route("calendar")
public class CalendarUI extends VerticalLayout implements AfterNavigationObserver{

	@Autowired
	CalendarEventStream calendarEventStream;
	
	VerticalLayout ceLayout;
	
	public CalendarUI() {
		
		H2 title = new H2("Calendar"); 
		add(title);
		title.setId("view-title");
		
		ceLayout = new VerticalLayout();
		add(ceLayout);
		
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
		calendarEventStream.listen(ce->{
			
			Div cdDiv = new Div();
			cdDiv.add(new Text(ce.subject()));
			cdDiv.setClassName("calendar-event");
			
			ceLayout.getUI()
				.get()
				.access(()->{
					
					ceLayout.add(cdDiv);
					
					ceLayout.getUI()
						.get()
						.push();
					
				});
			
		});
		
	}
	
}
