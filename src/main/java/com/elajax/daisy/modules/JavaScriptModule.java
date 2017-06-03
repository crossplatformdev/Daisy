package com.elajax.daisy.modules;

import java.sql.SQLException;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.elajax.daisy.utils.JavaScriptThread;

@Component("javascriptmodule")
@PropertySource("classpath:Daisy.properties")
public class JavaScriptModule {
	@Autowired
	private JavaScriptThread javaScriptThread;

	public JavaScriptModule(@Value("${db.url}") String url, @Value("${db.user}") String user,
			@Value("${db.pw}") String pw) throws SQLException, ScriptException {
		javaScriptThread = new JavaScriptThread(url, user, pw);
		reload();
	}

	public String evaluate(String text) throws ScriptException, SQLException, InterruptedException {
		return javaScriptThread.evaluate(text);
	}

	public void reload() throws SQLException, ScriptException {
		javaScriptThread.reload();
	}
}
