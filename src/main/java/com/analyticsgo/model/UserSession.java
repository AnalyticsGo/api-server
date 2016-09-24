package com.analyticsgo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class UserSession extends BaseToken {

  @NotNull
  @Setter
  @Getter
  private String ip;

  @NotNull
  @Column(columnDefinition = "text")
  @Setter
  @Getter
  private String userAgent;

}
