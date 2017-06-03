package com.elajax.daisy.utils;

import java.sql.SQLException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.hadoop.util.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.elajax.daisy.dao.JavaScriptDAO;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

@PropertySource("classpath:Daisy.properties")
@Component("javascriptthread")
public class JavaScriptThread {
	private static ScriptEngineManager mgr;

	private static ScriptEngine engine;
	private static Dao<JavaScriptDAO, Integer> dao;

	private String result = null;
	private String text;

	private Long timeout = 1000L;
    public volatile boolean flag = true;
	private Thread t;
	private Runnable r = new Runnable() {
		@Override
		public void run() {
			while (flag && result == null) {
				try {
					result = eval();					
				} catch (ScriptException e) {
					e.printStackTrace();
					result = e.getMessage();					
				} catch (SQLException e) {
					e.printStackTrace();
					result = e.getMessage();
				}
				flag = false;
			}
		}
	};

	public JavaScriptThread(@Value("${db.url}") String url, @Value("${db.user}") String user,
			@Value("${db.pw}") String pw) throws SQLException, ScriptException {
		JdbcConnectionSource connectionSource = new JdbcConnectionSource(url, user, pw);
		dao = DaoManager.createDao(connectionSource, JavaScriptDAO.class);
		TableUtils.createTableIfNotExists(connectionSource, JavaScriptDAO.class);
		reload();
	}

	public void reload() throws SQLException, ScriptException {
		mgr = new ScriptEngineManager();
		engine = mgr.getEngineByName("JavaScript");
		for (JavaScriptDAO js : dao.queryForAll()) {
			engine.eval(js.toString());
		}
	}

	public synchronized String evaluate(String text){
		synchronized (this) {
			Long time = Time.now();
			Long later = 0L;
			
			this.text = text;
			
			t = new Thread(r);
			flag = true;
			t.start();
			
			while (result == null) {
				try {
					Thread.sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
					result = e.getMessage();
				}
				later = Time.now();
			}
			String ret = result;
			
			flag = false;
			result = null;
			t = null;
			text = null;
			
			return ret;
		}

	}

	public String eval() throws ScriptException, SQLException {
		if (text.startsWith("save")) {
			text = text.replaceFirst("save", "");
			JavaScriptDAO js = new JavaScriptDAO();
			js.setJs(text);
			text = String.valueOf(engine.eval(text));
			@SuppressWarnings("unused")
			int s = dao.create(js);
		} else {
			text = String.valueOf(engine.eval(text));
		}
		return text;
	}
}
