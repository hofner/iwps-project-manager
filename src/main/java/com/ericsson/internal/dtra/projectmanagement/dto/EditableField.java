package com.ericsson.internal.dtra.projectmanagement.dto;

public class EditableField {

  private String field;
  private String normalizedPermission;

  public EditableField(String field, String normalizedPermission) {
    this.field = field;
    this.normalizedPermission = normalizedPermission;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getNormalizedPermission() {
    return normalizedPermission;
  }

  public void setNormalizedPermission(String normalizedPermission) {
    this.normalizedPermission = normalizedPermission;
  }
}
