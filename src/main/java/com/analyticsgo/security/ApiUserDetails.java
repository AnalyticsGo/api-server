package com.analyticsgo.security;

import com.analyticsgo.model.User;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class ApiUserDetails extends org.springframework.security.core.userdetails.User {

  @Getter
  private final AuthMethod authMethod;

  public ApiUserDetails(User user, AuthMethod authMethod) {
    super(user.getUsername(), user.getPasswordHash(), createAuthorities(user, authMethod));
    this.authMethod = authMethod;
  }

  public Authentication createAuthentication() {
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(this, getPassword(), getAuthorities());
    auth.eraseCredentials();
    return auth;
  }

  private static List<GrantedAuthority> createAuthorities(User user, AuthMethod authMethod) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(UserRole.READ.getAuthority());
    boolean readOnly = AuthMethod.API_KEY == authMethod;
    if (!readOnly) {
      authorities.add(UserRole.WRITE.getAuthority());
      if (user.isAdmin()) {
        authorities.add(UserRole.ADMIN.getAuthority());
      }
    }
    return authorities;
  }

}
