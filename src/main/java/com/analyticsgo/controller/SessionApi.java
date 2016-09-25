package com.analyticsgo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/session")
public class SessionApi {

  @GetMapping("/testAuth")
  public Greeting testAuth(String name) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Greeting greeting = new Greeting();
    greeting.message = String.format("Hello, %s!", StringUtils.isNotBlank(name) ? name : "World");
    greeting.timestamp = new Date();
    greeting.principal = auth.getPrincipal();
    greeting.credentials = auth.getCredentials();
    return greeting;
  }

  public static class Greeting {

    public String message;
    public Date timestamp;
    public Object principal;
    public Object credentials;

  }

}
