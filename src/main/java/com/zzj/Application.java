package com.zzj;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,JmxAutoConfiguration.class})
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class)
public class Application extends WebMvcConfigurerAdapter {

    private static Logger logger = Logger.getLogger(Application.class);


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setWebEnvironment(true);
        application.run(args);

    }



}
