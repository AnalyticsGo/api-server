package com.analyticsgo.security;

import com.analyticsgo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum UserRole {

  READ, WRITE, ADMIN;

  private GrantedAuthority authority;

  UserRole() {
    authority = new SimpleGrantedAuthority("ROLE_" + name());
  }

  public static List<GrantedAuthority> createAuthorities(User user, boolean readOnly) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(READ.authority);
    if (!readOnly) {
      authorities.add(WRITE.authority);
    }
    if (user.isAdmin()) {
      authorities.add(ADMIN.authority);
    }
    return authorities;
  }

}
