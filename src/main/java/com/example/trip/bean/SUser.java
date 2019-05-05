package com.example.trip.bean;



/**
 * 用户名密码信息
 * @author Gu
 */

public class SUser {
	private int id;
	private String name;
	private String password;

	public SUser(SUser sUser) {
		this.id = sUser.getId();
		this.name = sUser.getName();
		this.password = sUser.getPassword();
	}

	@Override
	public String toString() {
		return "SUser{" +
				"id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	public SUser() {
	}

	public SUser(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
