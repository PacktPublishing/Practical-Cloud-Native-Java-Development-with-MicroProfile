package com.packt.microprofile.book.ch6.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

@ApplicationScoped
@Startup
public class StartupCheck implements HealthCheck {

	final String NAME = "startupCheck";
	
	/*
	 * For the purpose of the code sample we will
	 * just always return "UP"
	 * 
	 */
    public HealthCheckResponse call() {
            return HealthCheckResponse.up(NAME);
    }
}
