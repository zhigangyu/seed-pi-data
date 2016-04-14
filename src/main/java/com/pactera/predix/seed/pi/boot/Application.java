package com.pactera.predix.seed.pi.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration(exclude = {

})
@ComponentScan(basePackages = "com.pactera.predix.seed.pi")
@SpringBootApplication
public class Application  {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		ApplicationContext ctx = springApplication.run(args);
	}

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
		return new TomcatEmbeddedServletContainerFactory();
	}
	
	

}
