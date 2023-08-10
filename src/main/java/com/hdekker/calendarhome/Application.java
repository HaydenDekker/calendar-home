package com.hdekker.calendarhome;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "calendarhome")
@Push
public class Application implements AppShellConfigurator {

    private static final long serialVersionUID = -4336210821702850507L;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
