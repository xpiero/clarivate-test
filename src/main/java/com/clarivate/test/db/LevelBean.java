package com.clarivate.test.db;

import java.io.Serializable;

public class LevelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
