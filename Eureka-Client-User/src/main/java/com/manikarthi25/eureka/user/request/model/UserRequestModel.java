package com.manikarthi25.eureka.user.request.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestModel {

	@NotNull(message = "First Name can not bel null")
	private String firstName;

	@NotNull(message = "First Name can not bel null")
	private String lastName;

	@NotNull(message = "First Name can not bel null")
	@Email
	private String email;

	@NotNull(message = "First Name can not bel null")
	@Size(min = 3, max = 10)
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
