package com.elajax.daisy.dao;

import org.springframework.stereotype.Component;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "javascript")
@Component("javascriptdao")
public class JavaScriptDAO {
	@DatabaseField(generatedId=true)
	private Integer id; // /< Unique identifier for this chat

	public String getJs() {
		return js;
	}

	@DatabaseField(dataType = DataType.LONG_STRING)
	private String js;

	public JavaScriptDAO() {

	}

	public void setJs(String js) {
		this.js = js;
	}

	@Override
	public String toString() {
		return this.js;
	}
}
