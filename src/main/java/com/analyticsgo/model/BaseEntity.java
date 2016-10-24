package com.analyticsgo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@EqualsAndHashCode(of = "id")
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Setter
  @Getter
  private Long id;

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
