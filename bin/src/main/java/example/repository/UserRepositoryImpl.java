package example.repository;

import example.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.ArrayList;

@Repository
public class UserRepositoryImpl implements UserRepository {

    final String[] ROLE = {"ROLE_USER"};

    @Autowired
  	public InMemoryUserDetailsManager inMemoryUserDetailsManager;

  	@Autowired
  	public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails findByName(String name){
      try {
        return inMemoryUserDetailsManager.loadUserByUsername(name);
      } catch(Exception e) {}
        return null;
    }

    @Override
    public User resetPassword(String name, String newPassword){
      User user = null;
      boolean userExists = inMemoryUserDetailsManager.userExists(name);
      if (userExists){
        user = new User();
        user.setUsername(name);
        user.setPassword(newPassword);
        inMemoryUserDetailsManager.deleteUser(name);
      }
      return user;
    }

    @Override
    public void save(example.model.User user){
      this.createUser(user);
    }

    private void createUser(example.model.User user){
      String name = user.getUsername();
      String password = user.getPassword();
      ArrayList<GrantedAuthority> grantedAuthoritiesList= new ArrayList<>();
      for (String role : ROLE) {
        grantedAuthoritiesList.add(new SimpleGrantedAuthority(role));
      }

      inMemoryUserDetailsManager
      .createUser(new org.springframework.security.core.userdetails.User(name, passwordEncoder.encode(password), grantedAuthoritiesList));
    }
}
