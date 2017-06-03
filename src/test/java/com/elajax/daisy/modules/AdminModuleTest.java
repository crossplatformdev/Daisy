package com.elajax.daisy.modules;

import java.sql.SQLException;

import javax.script.ScriptException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.pengrad.telegrambot.model.User;

@SpringBootApplication
@Configuration
@ComponentScan("com.elajax.daisy")
@PropertySource("classpath:Daisy.properties")
public class AdminModuleTest {
//	private @Value("${token}") String token;
//	
//	@Autowired
//	private AdminModule adminModule;
//
//	@Before
//	public void initialize() {
//		adminModule = new AdminModule(token);
//	}
//
//	@Test
//	public void adminTest(){
//		Assert.assertNotNull(adminModule);
//	}
//
//	@Test
//	public void rollTest(){
////		Assert.assertEquals((int)2,(int)Integer.valueOf(adminModule.rollDice("1d1+1")));
//	}
//	
//	@Test
//	public void mapTest(){
////		Assert.assertTrue(adminModule.sendMap(1000).length()!=32);
//	}
//	
//	@Test
//	public void thinkTest(){
//		Assert.assertFalse(adminModule.think("This is a test...").equals(""));
//	}
//	
//	@Test
//	public void authentifyTest(){
//		Assert.assertFalse(adminModule.authentify(new User(), token.substring(0, 5)).contains("password"));
//	}
	
//	@Test
//	public void evaluate1Test() throws NumberFormatException, ScriptException, SQLException{
//		Assert.assertTrue(2==Integer.valueOf(adminModule.evaluate("d(1)+1")));
//	}
//
//	@Test
//	public void evaluate2Test() throws NumberFormatException, ScriptException, SQLException{
//		Assert.assertTrue(1<=Integer.valueOf(adminModule.evaluate("r(1)+1")));
//	}
//
//	@Test
//	public void evaluate3Test() throws NumberFormatException, ScriptException, SQLException{
//		Assert.assertTrue(1<=Integer.valueOf(adminModule.evaluate("b(1)+1")));
//	}
//	
//	@Test
//	public void evaluate4Test() throws NumberFormatException, ScriptException, SQLException{
//		Assert.assertTrue(2==Integer.valueOf(adminModule.evaluate("1+1")));
//	}
}
