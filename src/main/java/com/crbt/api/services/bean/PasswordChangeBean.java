package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

public class PasswordChangeBean
{
  @NotNull
  private String msisdn;
  @NotNull
  private String password;
  
  public PasswordChangeBean() {}
  
  public PasswordChangeBean(String msisdn, String password) {
    this.msisdn = msisdn;
    this.password = password;
  }
  
  public String getMsisdn() { return msisdn; }
  
  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }
  
  public String getPassword() { return password; }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
