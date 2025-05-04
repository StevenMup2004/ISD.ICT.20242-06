package controller;

import model.DeliveryInfo;
import model.User;
import service.UserService;

public class UserController {
	private UserService userService;
	private User currentUser;
	private User selectedUser;
	private String selectedUsername;
	
	public UserController() {
		//userService = new UserService();
	}
	public int checkIfUserInformationMeetsPolicies(User user) {
		int err = 0;
		String usernameRegex = "^[a-zA-Z0-9._-]{1,20}$";
		String username = user.getUsername();
		if (!username.matches(usernameRegex)) err = err | 1;
		
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		String email = user.getEmail();
		if (!email.matches(emailRegex)) err = err | 2;
		
		String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		String password = user.getPassword();
		if (!password.matches(passwordRegex)) err = err | 4;
		
		return err;
	}
	void createNewUser() {
		User user = new User();
		// set user attribute by input components in fxml template
		boolean blank = false;
		if (user.getUsername() == "") {
			// notify "Username is blank now"
			blank = true;
		}
		if (user.getEmail() == "") {
			// notify "Email is blank now"
			blank = true;
		}
		if (user.getPassword() == "") {
			// notify "Password is blank now"
			blank = true;
		}
		if (blank) return;
		
		int error = checkIfUserInformationMeetsPolicies(user);
		if ((error & 1) != 0) {
			// notify "Username does not meet policy"
		}
		if ((error & 2) != 0) {
			// notify "Email does not meet policy"
		}
		if ((error & 4) != 0) {
			// notify "Password does not meet secure policy"
		}
		if (error != 0) return;
		
		if (!userService.checkIfUsernameAlreadyExists(user.getUsername())) {
			userService.save(user);
			// notify successful creation
		}
		else {
			// notify "Username already exists"
		}
	}
	void updateUserInformation() {
		// set selectedUser attributes based on input components 
		int err = checkIfUserInformationMeetsPolicies(selectedUser);
		if ((err & 1) != 0) {
			// notify "Username does not meet policy"

		}
		if ((err & 2) != 0) {
			// notify "Email does not meet policy"

		}
		if ((err & 4) != 0) {
			// notify "Password does not meet secure policy"

		}
		if (err != 0) return;
		
		if (selectedUser.getUsername() == selectedUsername || !userService.checkIfUsernameAlreadyExists(selectedUser.getUsername())) {
			userService.save(selectedUser);
			// notify successful creation
		}
		else {
			// notify username already exists
		}
	}
}
