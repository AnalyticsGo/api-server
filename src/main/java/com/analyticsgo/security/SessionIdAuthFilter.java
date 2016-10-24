package com.analyticsgo.security;

import com.analyticsgo.model.UserSession;
import com.analyticsgo.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
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
    ApiUserDetails userDetails = new ApiUserDetails(userSession.getUser(), AuthMethod.SESSION_ID);
    SecurityContextHolder.getContext().setAuthentication(userDetails.createAuthentication());
    filterChain.doFilter(request, response);
  }

}
