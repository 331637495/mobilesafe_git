package com.xawx.mobilesafe.domain;

public class ContactInfo {

	public String name;
	public String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ContactInfo() {

	}

	public ContactInfo(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

}
