package repository;

import java.util.List;

import model.User;

public class UserRepository {
	private List<User> users;
	public UserRepository(List<User> users) {
		this.users = users;
	}
	public User getUserByUserID(int userID) {
		User user = new User();
		return user;
	}
	public void save(User user) {
		users.add(user);
	}
	
	public List<User> getAllUsers() {
		return users;
	}
}
