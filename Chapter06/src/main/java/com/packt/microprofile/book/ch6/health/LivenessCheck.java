package com.packt.microprofile.book.ch6.health;

import java.lang.management.ManagementFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
public class LivenessCheck implements HealthCheck {

    public HealthCheckResponse call() {

        // Percentage value from 0.0-1.0
        Double memoryUsage = getMemUsage();

        /*
         * Alternatively, you can use the the below line instead which uses the status()
         * method. No need for an if-else block!
         */
        // return HealthCheckResponse.builder().name("LivenessCheck").status(memoryUsage
        // < 0.9).withData("MemoryUsage", memoryUsage.toString()).build();

        HealthCheckResponseBuilder builder = HealthCheckResponse.named("LivenessCheck");
        if (memoryUsage < 0.9) {
            builder.up();
        } else {
            builder.down();
        }
        builder = builder.withData("MemoryUsage", memoryUsage.toString());
        return builder.build();

    }

    public Double getMemUsage() {
        return Utility.getMemUsage();
    }
}
