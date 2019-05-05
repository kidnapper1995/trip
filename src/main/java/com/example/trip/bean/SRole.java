package com.example.trip.bean;


/**
 * 角色信息
 * @author Gu
 */
public class SRole {
	private int id;

	@Override
	public String toString() {
		return "SRole{" +
				"id=" + id +
				", role='" + role + '\'' +
				", describe='" + describe + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public SRole() {
	}

	public SRole(int id, String role, String describe) {
		this.id = id;
		this.role = role;
		this.describe = describe;
	}

	private String role;
	private String describe;
}
