package com.elajax.daisy;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@ComponentScan("com.elajax.daisy")
@PropertySource("classpath:Daisy.properties")
public class TelegramBotTest {
//
//	private @Value("${token}") String token;
//
//	@Test
//	public void echoTest() {
//		System.out.println("Starting");
//		EchoBot bot = new EchoBot(this.token);
//		Assert.assertNotNull(bot);
//	}
//
//	@Test
//	public void diceyTest() {
//		System.out.println("Starting");
//		DiceyBot bot = new DiceyBot(this.token);
//		Assert.assertNotNull(bot);
//	}
}
