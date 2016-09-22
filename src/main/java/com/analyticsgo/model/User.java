package com.analyticsgo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "aguser")
@ToString(of = "username")
public class User extends BaseEntity {

  @NotNull
  @Column(unique = true)
  @Setter
  @Getter
  private String username;

  @NotNull
  @Setter
  @Getter
  private String passwordHash;

}
