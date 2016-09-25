package com.analyticsgo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "app_user")
@ToString(of = "username")
public class User extends BaseEntity {

  @NotNull
  @Column(unique = true)
  @Setter
  @Getter
  private String username;

  @NotNull
  @JsonIgnore
  @Setter
  @Getter
  private String passwordHash;

  @Setter
  public Boolean admin;

  public boolean isAdmin() {
    return Boolean.TRUE.equals(admin);
  }

}
