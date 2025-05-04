package testing;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import controller.UserController;
import model.User;

public class UserControllerTest {
	UserController userController;
	
	@Before
	public void setUp() {
		userController = new UserController();
	}
	
	private User createUser(String username, String email, String password) {
	    return new User(
	      1,
	      username,
	      email,
	      password,
	      "admin",
	      true,
	      LocalDateTime.now().minusDays(1),
	      LocalDateTime.now()
	    );
	}
	@Test
	public void testCheckIfUserInformationMeetsPolicies() {
		User user;
		
		user = createUser("validUser_123", "user@example.com", "Strong1@pass");
	    assertEquals(0, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("invalid user!", "user@example.com", "Strong2@pass");
	    assertEquals(1, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("validUser", "invalidemail@", "Strong1@pass");
	    assertEquals(2, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("validUser", "user@example.com", "weakpass");
	    assertEquals(4, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("bad!user", "bademail", "Strong1@pass");
	    assertEquals(3, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("bad user", "user@example.com", "nopass");
	    assertEquals(5, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("validUser", "bad@", "123");
	    assertEquals(6, userController.checkIfUserInformationMeetsPolicies(user));
	    
	    user = createUser("!", "noemail", "123");
	    assertEquals(7, userController.checkIfUserInformationMeetsPolicies(user));
	}
}
