package com.analyticsgo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@EqualsAndHashCode(of = "id")
public class BaseToken {

  @Id
  @Setter
  @Getter
  private String id;

  @NotNull
  @ManyToOne
  @Setter
  @Getter
  private User user;

  @NotNull
  @Column(columnDefinition = "timestamp with time zone")
  @Temporal(TemporalType.TIMESTAMP)
  @Setter
  @Getter
  private Date createdTime;

  @PrePersist
  protected void onCreate() {
    createdTime = new Date();
  }

}
