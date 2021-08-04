package example.service;

import example.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
  void save(User user);
  User resetPassword(String username, String newPassword);
  UserDetails findByUsername(String username);
}
