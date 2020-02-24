package com.ericsson.internal.dtra.projectmanagement.dto;

import java.util.Collection;
import java.util.Map;

import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;

/**
 * Object combining workflow action rules and workflow edit rules
 * Main usage will be to return all the rules together for frontend
 * @author ejeagir
 *
 */
public class WorkflowRules {

  private Map<WorkLevel, Map<String, Collection<WorkflowAction>>> workflowActionRules;

  private Map<String, Map<WorkLevel, Map<String, String>>> workflowEditRules;

  public WorkflowRules(Map<WorkLevel, Map<String, Collection<WorkflowAction>>> workflowActionRules,
        Map<String, Map<WorkLevel, Map<String, String>>> workflowEditRules) {
    this.workflowActionRules = workflowActionRules;
    this.workflowEditRules = workflowEditRules;
  }

  public Map<WorkLevel, Map<String, Collection<WorkflowAction>>> getWorkflowActionRules() {
    return workflowActionRules;
  }

  public void setWorkflowActionRules(Map<WorkLevel, Map<String, Collection<WorkflowAction>>> workflowActionRules) {
    this.workflowActionRules = workflowActionRules;
  }

  public Map<String, Map<WorkLevel, Map<String, String>>> getWorkflowEditRules() {
    return workflowEditRules;
  }

  public void setWorkflowEditRules(Map<String, Map<WorkLevel, Map<String, String>>> workflowEditRules) {
    this.workflowEditRules = workflowEditRules;
  }
}
