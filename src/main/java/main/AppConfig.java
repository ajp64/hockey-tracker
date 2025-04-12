package main;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"service", "repository", "aspects", "controller", "converter"})
public class AppConfig {
}
