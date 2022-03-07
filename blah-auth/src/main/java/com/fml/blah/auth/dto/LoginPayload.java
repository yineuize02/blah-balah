package com.fml.blah.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/** @author yrz */
@Data
public class LoginPayload {
  @NotBlank private String username;
  @NotBlank private String password;
}
