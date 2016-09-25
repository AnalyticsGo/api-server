package com.analyticsgo.security;

import com.analyticsgo.model.ApiKey;
import com.analyticsgo.model.User;
import com.analyticsgo.service.ApiKeyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

  private final ApiKeyService apiKeyService;

  public ApiKeyAuthFilter(ApiKeyService apiKeyService) {
    this.apiKeyService = apiKeyService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String apiKeyId = request.getParameter("apiKey");
    if (StringUtils.isBlank(apiKeyId)) {
      apiKeyId = request.getHeader("X-Api-Key");
    }
    if (StringUtils.isBlank(apiKeyId)) {
      filterChain.doFilter(request, response);
      return;
    }
    ApiKey apiKey = apiKeyService.findById(apiKeyId);
    if (apiKey == null || apiKey.isDisabled()) {
      throw new BadCredentialsException(String.format("Invalid api key [%s]", apiKeyId));
    }
    User user = apiKey.getUser();
    org.springframework.security.core.userdetails.User principal =
        new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPasswordHash(), UserRole.createAuthorities(user, true));
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal,
        principal.getPassword(), principal.getAuthorities());
    auth.eraseCredentials();
    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);
  }

}
