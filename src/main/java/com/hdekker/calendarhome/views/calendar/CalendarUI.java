package com.hdekker.calendarhome.views.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hdekker.calendarhome.outlook.CalendarEvent;
import com.hdekker.calendarhome.outlook.CalendarEventStream;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("calendar")
@RouteAlias(value = "")
public class CalendarUI extends VerticalLayout implements AfterNavigationObserver{

	Logger log = LoggerFactory.getLogger(CalendarUI.class);
	
	@Autowired
	CalendarEventStream calendarEventStream;
	
	VerticalLayout ceLayout;
	
	public CalendarUI() {
		
		ceLayout = new VerticalLayout();
		add(ceLayout);
		
	}
	
	Component createCalendarCard(CalendarEvent ce) {
		
		VerticalLayout cdDiv = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		cdDiv.add(hl);
	
		DateTimePicker dtpStartTime = new DateTimePicker();
		dtpStartTime.setClassName("calendar-dt");
		dtpStartTime.setValue(ce.startTime());
		dtpStartTime.setReadOnly(true);
		
		hl.add(new H3(ce.subject()));
		hl.add(dtpStartTime);
		
		hl.addClassName("calendar-event-subject-header");
		cdDiv.setClassName("calendar-event"); 
		
		return cdDiv;
		
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {

		calendarEventStream.listen(ce->{
			
			Component cdDiv = createCalendarCard(ce);
			
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
