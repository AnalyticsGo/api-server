package com.analyticsgo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@ToString(of = "name")
public class ApiKey extends BaseToken {

  @NotNull
  @Setter
  @Getter
  private String name;

  @Setter
  private Boolean disabled;

  public boolean isDisabled() {
    return Boolean.TRUE.equals(disabled);
  }

}
