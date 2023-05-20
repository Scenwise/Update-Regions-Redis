package nl.scenwise.regionUpdater;

import nl.scenwise.regionUpdater.updator.RegionsParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;


@SpringBootApplication
@EnableSpringConfigured
public class RegionUpdaterRedis {
    public static void main(String[] args) {
        SpringApplication.run(RegionUpdaterRedis.class, args);
    }
}
