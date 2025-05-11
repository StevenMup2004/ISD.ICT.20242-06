// Bui Xuan Son - 20226065 - Manage User
/*
 * Cohesion Level: Functional Cohesion
 * 
 * SRP Violation: No
 * This controller has good cohesion - all methods work together to accomplish 
 * the single responsibility of retrieving and displaying product details.
 * 
 * Improvement: No significant improvements needed.
 */

package service;

import java.util.List;

import model.User;
import repository.UserRepository;

public class UserService {
  UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public boolean checkIfUsernameAlreadyExists(String username) {
    List<User> users = userRepository.getAllUsers();
    for (User user : users) {
      if (user.getUsername() == username)
        return true;
    }
    return false;
  }

}
