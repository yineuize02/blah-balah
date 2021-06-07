package com.fml.blah.common.exception;

public class ServerErrorException extends RuntimeException {
  public ServerErrorException() {
    super();
  }

  public ServerErrorException(String message) {
    super(message);
  }
}
