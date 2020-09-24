package org.acme;


import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.net.InetAddress;

@ApplicationScoped
@Liveness
public class MyLivenessCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        String hostname;

        try {
            hostname=InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {
            hostname="Unknown";
        }

        return  HealthCheckResponse
                .named("My Liveness check")
                .up()
                .withData("hostname", hostname)
                .build();
    }
}