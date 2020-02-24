package com.ericsson.internal.dtra.projectmanagement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientConfig {

  @Value("${spd_gateway.url}")
  private String spdGatewayUrl;

  @Value("${cost_model_project.url}")
  private String costModelProjectUrl;

  @Value("${cost_model_purchase_order.url}")
  private String costModelPurchaseOrderUrl;

  @Value("${cost_model_service_order.url}")
  private String costModelServiceOrderUrl;

  public String getSpdGatewayUrl() {
    return spdGatewayUrl;
  }

  public String getCostModelProjectUrl() {
    return costModelProjectUrl;
  }

  public String getCostModelPurchaseOrderUrl() {
    return costModelPurchaseOrderUrl;
  }

  public String getCostModelServiceOrderUrl() {
    return costModelServiceOrderUrl;
  }
}
