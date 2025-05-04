package model;

import java.time.LocalDateTime;

public class User {
	private int userID;
	private String username;
	private String email;
	private String password;
	private String role;
	private boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public User(int userID, String username, String email, String password,
            String role, boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.userID = userID;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public User() {
		
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
}
