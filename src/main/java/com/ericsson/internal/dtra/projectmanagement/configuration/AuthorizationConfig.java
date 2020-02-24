package com.ericsson.internal.dtra.projectmanagement.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "authorization")
public class AuthorizationConfig {

  private String url;
  private String clientId;
  private String secret;
  private String endpoint;
  private String username;
  private String password;

  public final String getUrl() {
    return url;
  }

  public final void setUrl(final String url) {
    this.url = url;
  }

  public final String getClientId() {
    return clientId;
  }

  public final void setClientId(final String clientId) {
    this.clientId = clientId;
  }

  public final String getSecret() {
    return secret;
  }

  public final void setSecret(final String secret) {
    this.secret = secret;
  }

  public final String getEndpoint() {
    return endpoint;
  }

  public final void setEndpoint(final String endpoint) {
    this.endpoint = endpoint;
  }

  public final String getUsername() {
    return username;
  }

  public final void setUsername(final String username) {
    this.username = username;
  }

  public final String getPassword() {
    return password;
  }

  public final void setPassword(String password) {
    this.password = password;
  }
}
