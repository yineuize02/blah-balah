package com.fml.blah.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fml.blah.user.entity.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties("password")
public class UserVo extends Users {}
