package com.analyticsgo.model;

import com.analyticsgo.model.json.JsonConverter;
import com.analyticsgo.model.json.SessionInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class UserSession extends BaseToken {

  private static final long TIMEOUT = 7 * 24 * 60 * 60 * 1000L;

  @NotNull
  @Column(columnDefinition = "text")
  @Convert(converter = SessionInfoConverter.class)
  @Setter
  @Getter
  private SessionInfo info;

  public boolean isExpired() {
    return System.currentTimeMillis() - getCreatedTime().getTime() > TIMEOUT;
  }

  public static class SessionInfoConverter extends JsonConverter<SessionInfo> {

    @Override
    protected TypeReference<SessionInfo> createTypeRef() {
      return new TypeReference<SessionInfo>() {};
    }

  }

}
