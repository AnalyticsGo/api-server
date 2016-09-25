package com.analyticsgo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserSession extends BaseToken {

  private static final long TIMEOUT = 7 * 24 * 60 * 60 * 1000L;

  @Setter
  @Getter
  private String ip;

  @Column(columnDefinition = "text")
  @Setter
  @Getter
  private String userAgent;

  public boolean isExpired() {
    return System.currentTimeMillis() - createdTime.getTime() > TIMEOUT;
  }

}
