package com.analyticsgo.security;

import com.analyticsgo.model.User;
import com.analyticsgo.model.UserSession;
import com.analyticsgo.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionIdAuthFilter extends OncePerRequestFilter {

  private final UserSessionService userSessionService;

  public SessionIdAuthFilter(UserSessionService userSessionService) {
    this.userSessionService = userSessionService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String sessionId = request.getHeader("X-Session-Id");
    if (StringUtils.isBlank(sessionId)) {
      filterChain.doFilter(request, response);
      return;
    }
    UserSession userSession = userSessionService.findById(sessionId);
    if (userSession == null || userSession.isExpired()) {
      throw new BadCredentialsException(String.format("Invalid session id [%s]", sessionId));
    }
    User user = userSession.getUser();
    org.springframework.security.core.userdetails.User principal =
        new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPasswordHash(), UserRole.createAuthorities(user, false));
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal,
        principal.getPassword(), principal.getAuthorities());
    auth.eraseCredentials();
    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);
  }

}
