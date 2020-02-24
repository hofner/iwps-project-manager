package com.ericsson.internal.dtra.projectmanagement.enums;

/**
 * This fields represents both the ones that are editable with permissions and the one without.
 * @author egumola
 *
 */
//TODO: Find a way to annotate the project for editable fields and fetch them in reflection mode (if not too slow)
public enum ProjectEditableField {

  SAP_PROJECT_ID("sapProjectId"),
  COMMENT("comment"),
  EXTERNAL_REFERENCE_ID("externalReferenceId"),
  BUNDLING("bundling"),
  STANDARD_NETWORK("standardNetwork"),
  SERVICE_PROJECT_MANAGER_SIGNUM("serviceProjectManagerSignum"),
  SERVICE_PROJECT_MANAGER_FULLNAME("serviceProjectManagerFullname"),
  ASSISTING_CUSTOMER_PROJECT_MANAGERS("assistingCustomerProjectManagers"),
  ASSISTING_SERVICE_PROJECT_MANAGERS("assistingServiceProjectManagers"),
  STATUS("status");

  private String fieldName;

  private ProjectEditableField(final String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

}
