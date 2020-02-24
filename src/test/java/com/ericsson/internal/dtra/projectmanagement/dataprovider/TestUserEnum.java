package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import java.util.Arrays;
import java.util.List;

public enum TestUserEnum {
  //TODO: add more users with complete permission sets
  ADMIN_USER("ADMIN_USER", Arrays.asList("/dtra")), CPM_USER("CPM_USER", Arrays.asList("/dtra/project/create"));

  private String username;
  private List<String> permissions;

  TestUserEnum(String username, List<String> permissions) {
    this.username = username;
    this.permissions = permissions;
  }

  public String getUsername() {
    return username;
  }

  public List<String> getPermissions() {
    return permissions;
  }

}

