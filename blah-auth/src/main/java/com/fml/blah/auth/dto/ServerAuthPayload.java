package com.fml.blah.auth.dto;

import lombok.Data;

/** @author yrz */
@Data
public class ServerAuthPayload {
  private String serverId;
  private String serverPassword;
}
