package com.albo.directory.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contact extends User{
	@Id
	private Long id;
	
	private Long phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
