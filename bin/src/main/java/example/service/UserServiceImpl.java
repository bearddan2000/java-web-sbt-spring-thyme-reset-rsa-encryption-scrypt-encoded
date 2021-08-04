package example.service;

import example.model.User;
import example.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails findByUsername(String username){
      return userRepository.findByName(username);
    }

    @Override
    public void save(User user) {
        user.setPassword(user.getPassword());
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        System.out.println("[LOG] user saved");
    }

    @Override
    public User resetPassword(String username, String newPassword) {
      User user = userRepository.resetPassword(username, newPassword);
      if (user == null) {
        System.out.println("[LOG] user not found");
      } else {
        userRepository.save(user);
        System.out.println("[LOG] user password changed");
      }
      return user;
    }
}
