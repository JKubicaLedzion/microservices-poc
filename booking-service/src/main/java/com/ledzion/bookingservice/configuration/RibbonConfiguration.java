package com.ledzion.bookingservice.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(final IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
