package com.fml.blah.remote_interface.auth.dto;

import lombok.Data;

/** @author yrz */
@Data
public class ServerAuthPayload {
  private String serverId;
  private String serverPassword;
}
