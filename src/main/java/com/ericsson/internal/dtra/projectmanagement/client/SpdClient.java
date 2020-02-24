package com.ericsson.internal.dtra.projectmanagement.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.ericsson.internal.dtra.projectmanagement.configuration.AuthorizationConfig;
import com.ericsson.internal.dtra.projectmanagement.configuration.ClientConfig;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.dto.spdgateway.SpdResponse;

import se.ericsson.internal.csdp.authorization.client.BaseClient;

public class SpdClient extends BaseClient {
  private static final String VERSION = "/v1";

  @Autowired
  private ClientConfig clientConfig;

  @Autowired
  public SpdClient(final AuthorizationConfig authorizationConfig) {
    super(authorizationConfig.getUrl(), authorizationConfig.getClientId(), authorizationConfig.getSecret(), authorizationConfig.getUsername(), authorizationConfig.getPassword());
  }

  public SpdResponse createWorkBreakDownStructure(Project project) {
    String url = clientConfig.getSpdGatewayUrl() + VERSION + "/projects";
    return getCSDPSecureRestTemplate().postForObject(url, project, SpdResponse.class);
  }

  public SpdResponse addWorkPackageToWorkBreakDownStructure(Project project) {
    String url = clientConfig.getSpdGatewayUrl() + VERSION + "/projects";
    return getCSDPSecureRestTemplate().secureExchange(url, HttpMethod.PUT, new HttpEntity<Project>(project), SpdResponse.class).getBody();
  }

  public SpdResponse addAssistingServiceProjectManagers(String projectID, List<String> assistingServiceProjectManagers) {
    String url = clientConfig.getSpdGatewayUrl() + VERSION + "/projects/" + projectID + "/assisting_service_project_managers";
    return getCSDPSecureRestTemplate().secureExchange(url, HttpMethod.PUT, new HttpEntity<List<String>>(assistingServiceProjectManagers), SpdResponse.class).getBody();
  }

  public SpdResponse updateWorkPackage(String workPackageId, WorkPackage workPackage) {
    String url = clientConfig.getSpdGatewayUrl() + VERSION + "/work_packages/" + workPackageId + "/status";
    return getCSDPSecureRestTemplate().secureExchange(url, HttpMethod.PUT, new HttpEntity<WorkPackage>(workPackage), SpdResponse.class).getBody();
  }
}
