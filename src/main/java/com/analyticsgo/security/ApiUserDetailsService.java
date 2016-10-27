package com.analyticsgo.security;

import com.analyticsgo.model.User;
import com.analyticsgo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ApiUserDetailsService implements UserDetailsService {

  private final UserService userService;

  public ApiUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
    }
    return new ApiUserDetails(user, AuthMethod.BASIC_AUTH);
  }

}
