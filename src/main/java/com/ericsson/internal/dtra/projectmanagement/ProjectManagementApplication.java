package com.ericsson.internal.dtra.projectmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.ericsson.internal.csdp.config.authorization.EnableAuthorization;

@EnableAuthorization(excluded = { "/v2/api-docs/**", "/health" })
@SpringBootApplication
public class ProjectManagementApplication {
  public static void main(final String[] args) {
    SpringApplication.run(ProjectManagementApplication.class, args);
  }
}
