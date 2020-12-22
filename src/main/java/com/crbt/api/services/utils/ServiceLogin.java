package com.crbt.api.services.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("service")
public class ServiceLogin
{
  private String username;
  private String password;
  
  public ServiceLogin() {}
  
  public String getUserName()
  {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
}
