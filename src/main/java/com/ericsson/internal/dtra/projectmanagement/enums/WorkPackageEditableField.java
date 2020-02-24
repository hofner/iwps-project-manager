package com.ericsson.internal.dtra.projectmanagement.enums;

/**
 * This fields represents both the ones that are editable with permissions and the one without.
 * @author egumola
 *
 */
//TODO: Find a way to annotate the project for editable fields and fetch them in reflection mode (if not too slow)
public enum WorkPackageEditableField {

  START_DATE("startDate"),
  COMMENTS("comments"),
  DUE_DATE("dueDate"),
  TECHNICAL_INPUT("technicalInput"),
  TECHNICAL_OUTPUT("technicalOutput"),
  NETWORK_ACTIVITY("networkActivity"),
  PURCHASE_ORDER("purchaseOrder"),
  OPERATIONAL_ACTIVITY("operationalActivity"),
  SERVICE_ORDER("serviceOrder"),
  STATUS("status");

  private String fieldName;

  private WorkPackageEditableField(final String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

}
