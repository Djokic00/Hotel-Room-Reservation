package sk.hotelreservationservice.configuration;


import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.hotelreservationservice.exception.NotFoundException;

import java.time.Duration;

@Configuration
public class RetryConfiguration {
    @Bean
    public Retry userServiceRetry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(5)
                .intervalFunction(IntervalFunction.ofExponentialRandomBackoff(Duration.ofMillis(2000), 2, 0.3))
                .ignoreExceptions(NotFoundException.class)
                .build();
        RetryRegistry registry = RetryRegistry.of(config);

        return registry.retry("userServiceRetry");
    }

}
