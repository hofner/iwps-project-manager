package com.ericsson.internal.dtra.projectmanagement.enums;

public enum WorkLevel {

  PROJECT("Project"), WBS("WBS"), WP("WP");

  private String level;

  WorkLevel(final String level) {
    this.level = level;
  }

  public String getLevel() {
    return this.level;
  }

}
