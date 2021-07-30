package com.packt.microprofile.book.ch6.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
@Readiness
public class ReadinessCheck implements HealthCheck {

    final String NAME = "evenNumberPhobic";

    public HealthCheckResponse call() {
        long time = System.currentTimeMillis();

        if (time % 2 == 0)
            return HealthCheckResponse.down(NAME);
        else
            return HealthCheckResponse.up(NAME);
    }
}
