
package ru.rejchev.pstu.enrollstat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
@EntityScan("ru.rejchev.pstu.enrollstat")
@ConfigurationPropertiesScan("ru.rejchev.pstu.enrollstat")
public class EnrollStat {

    public static void main(String[] args) {
        SpringApplication.run(EnrollStat.class, args);
    }
}