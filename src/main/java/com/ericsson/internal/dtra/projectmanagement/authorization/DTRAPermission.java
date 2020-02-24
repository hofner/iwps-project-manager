package com.ericsson.internal.dtra.projectmanagement.authorization;

public class DTRAPermission {

  public static final String PROJECT_VIEW = "/dtra/project/view";
  public static final String PROJECT_DETAILS_VIEW = "/dtra/project/details/view";
  public static final String PROJECT_DETAILS_EDIT = "/dtra/project/details/edit";
  public static final String PROJECT_CREATE = "/dtra/project/create";
  public static final String PROJECT_VIEW_ALL = "/dtra/project/view_all";
  public static final String CHANGE_EVENT_VIEW = "/dtra/project/change_event/view";
  public static final String WBS_CREATE = "/dtra/project/wbs/create";
  public static final String WBS_EDIT = "/dtra/project/wbs/edit";
  public static final String WBS_DELETE = "/dtra/project/wbs/delete";
  public static final String WP_CREATE = "/dtra/project/wp/create";
  public static final String WP_EDIT = "/dtra/project/wp/edit";
  public static final String WP_DELETE = "/dtra/project/wp/delete";
  public static final String AUTOMATIC_ACTION = "/dtra/project/automatic_action";
  public static final String ADMIN = "/dtra";

  private DTRAPermission() {
    // Private constructor
  }
}
