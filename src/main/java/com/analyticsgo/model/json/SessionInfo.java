package com.analyticsgo.model.json;

import lombok.Getter;
import lombok.Setter;

public class SessionInfo {

  @Setter
  @Getter
  private String ip;

  @Setter
  @Getter
  private String userAgent;

}
