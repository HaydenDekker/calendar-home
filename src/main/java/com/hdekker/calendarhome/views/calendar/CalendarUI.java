package com.hdekker.calendarhome.views.calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.calendarhome.outlook.CalendarEvent;
import com.hdekker.calendarhome.outlook.CalendarEventStream;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
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
	
	Component createCalendarCard(CalendarEvent ce) {
		
		VerticalLayout cdDiv = new VerticalLayout();
		cdDiv.add(new H3(ce.subject()));
		cdDiv.add(new Label(ce.body()));
		cdDiv.add(new Label(ce.location().address.city));
		
		DateTimePicker dtpStartTime = new DateTimePicker();
		dtpStartTime.setValue(ce.startTime());
		dtpStartTime.setReadOnly(true);
		cdDiv.add(dtpStartTime);
		
		DateTimePicker dtpFinishTime = new DateTimePicker();
		dtpFinishTime.setValue(ce.finishTime());
		dtpFinishTime.setReadOnly(true);
		cdDiv.add(dtpFinishTime);
		
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
