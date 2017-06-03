package com.elajax.daisy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@SpringBootApplication
@ComponentScan("com.elajax.daisy")
@PropertySource("classpath:Daisy.properties")
public class Main {
	private static 	@Value("${token}") String token;
	@Autowired
	private static DiceyBot diceyBot;
	
	private static ApplicationContext context;

	public static void main(String[] args) throws InterruptedException{
		context = new AnnotationConfigApplicationContext(
                Main.class);
        diceyBot = (DiceyBot) context.getBean("diceybot");
        diceyBot.run();
	}
}
