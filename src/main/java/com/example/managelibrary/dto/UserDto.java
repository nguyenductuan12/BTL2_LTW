package com.example.managelibrary.dto;

import java.io.Serializable;


public class UserDto implements Serializable {
    private String email;
    private String userDisplayName;
    private String password;
    private String checkPass;
    private String role;
    
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserDisplayName() {
		return userDisplayName;
	}
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckPass() {
		return checkPass;
	}
	public void setCheckPass(String checkPass) {
		this.checkPass = checkPass;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

    
	
}
