package com.analyticsgo.security;

import com.analyticsgo.model.User;
import com.analyticsgo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApiUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPasswordHash(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
  }

}
