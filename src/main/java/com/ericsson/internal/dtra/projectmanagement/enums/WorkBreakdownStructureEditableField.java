package com.ericsson.internal.dtra.projectmanagement.enums;

/**
 * This fields represents both the ones that are editable with permissions and the one without.
 * @author egumola
 *
 */
//TODO: Find a way to annotate the project for editable fields and fetch them in reflection mode (if not too slow)
public enum WorkBreakdownStructureEditableField {

  NAME("name"),
  LABEL("label"),
  SITE("site"),
  COMMENTS("comments"),
  START_DATE("startDate"),
  DUE_DATE("dueDate"),
  TECHNICAL_INPUT("technicalInput"),
  TECHNICAL_OUPUT("technicalOutput"),
  SUB_NETWORK("subNetwork"),
  NETWORK_ACTIVITY("networkActivity"),
  PURCHASE_ORDER("purchaseOrder"),
  OPERATIONAL_ACTIVITY("operationalActivity"),
  STATUS("status");

  private String fieldName;

  private WorkBreakdownStructureEditableField(final String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

}
