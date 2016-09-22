package com.analyticsgo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@ToString(of = "name")
public class DataTable extends BaseEntity {

  @NotNull
  @ManyToOne(cascade = CascadeType.ALL)
  @Setter
  @Getter
  public User owner;

  @NotNull
  @Setter
  @Getter
  public String name;

}
