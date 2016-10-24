package com.analyticsgo.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

  READ, WRITE, ADMIN;

  @Getter
  private final GrantedAuthority authority;

  UserRole() {
    authority = new SimpleGrantedAuthority("ROLE_" + name());
  }

}
