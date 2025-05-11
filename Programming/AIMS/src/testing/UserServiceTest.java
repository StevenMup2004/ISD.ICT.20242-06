// writer: Bui Xuan Son - 20226065
package testing;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.User;
import repository.UserRepository;
import service.UserService;

public class UserServiceTest {
	List<User> users;
	UserRepository userRepository;
	UserService userService;
	
	private User createUser(int userID, String username, String email, String password) {
	    return new User(
	      userID,
	      username,
	      email,
	      password,
	      "admin",
	      true,
	      LocalDateTime.now().minusDays(1),
	      LocalDateTime.now()
	    );
	}
	@Before
	public void setUp() {
		users = new ArrayList<>();
		users.add(createUser(1, "alice123", "alice@example.com", "Password@123"));
	    users.add(createUser(2, "bob_smith", "bob.smith@example.com", "SecurePass1!"));
	    users.add(createUser(3, "charlie", "charlie.mail@domain.com", "Test1234$"));
	    userRepository = new UserRepository(users);
	    userService = new UserService(userRepository);
	    
	}
	@Test
	public void testCheckIfUsernameAlreadyExists1() {
		assertEquals(true, userService.checkIfUsernameAlreadyExists("alice123"));
	}
	
	@Test
	public void testCheckIfUsernameAlreadyExists2() {
		assertEquals(false, userService.checkIfUsernameAlreadyExists("thomas124"));
	}
}
