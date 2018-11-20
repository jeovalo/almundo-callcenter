package com.jeovalo.almundo.callcenter.api.exception;

public class ApiException extends Exception{
  private static final long serialVersionUID = 6769244671271822191L;
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
