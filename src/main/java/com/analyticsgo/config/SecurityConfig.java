package com.analyticsgo.config;

import com.analyticsgo.security.ApiKeyAuthFilter;
import com.analyticsgo.security.ApiUserDetailsService;
import com.analyticsgo.security.SessionIdAuthFilter;
import com.analyticsgo.service.ApiKeyService;
import com.analyticsgo.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final ApiUserDetailsService apiUserDetailsService;
  private final ApiKeyService apiKeyService;
  private final UserSessionService userSessionService;

  @Autowired
  public SecurityConfig(ApiUserDetailsService apiUserDetailsService, ApiKeyService apiKeyService,
      UserSessionService userSessionService) {
    this.apiUserDetailsService = apiUserDetailsService;
    this.apiKeyService = apiKeyService;
    this.userSessionService = userSessionService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(new ApiKeyAuthFilter(apiKeyService), BasicAuthenticationFilter.class);
    http.addFilterBefore(new SessionIdAuthFilter(userSessionService),
        BasicAuthenticationFilter.class);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.csrf().disable();
    http.httpBasic();
    http.authorizeRequests().anyRequest().authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    auth.userDetailsService(apiUserDetailsService);
  }

}
