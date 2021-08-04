package example.repository;

import example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository {
    UserDetails findByName(String name);
    User resetPassword(String name, String newPassword);
    void save(User user);
}
