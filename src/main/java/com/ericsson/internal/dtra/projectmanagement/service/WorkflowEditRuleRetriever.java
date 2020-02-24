package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowEditRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;

@Service
public class WorkflowEditRuleRetriever {

  @Autowired
  private WorkflowEditRuleRepository workflowEditRuleRepository;

  @Autowired
  private StatusRetriever statusRetriever;

  /**
   * Goes through all workflow edit rules and builds a map to be used to allow editing of Project/WBS/WP fields
   * This map is From Current Project Status To WorkLevel (of the field) To editableField To requiredPermissionToEdit
   * @return A map of all workflow edit rules
   */
  public Map<String, Map<WorkLevel, Map<String, String>>> buildWorkflowEditRulesMap() {
    List<WorkflowEditRule> workflowEditRules = workflowEditRuleRepository.findAll();
    Set<String> projectStatusNames = statusRetriever.getStatusNames(Arrays.asList(WorkLevel.PROJECT));

    Map<String, Map<WorkLevel, Map<String, String>>> workflowEditRulesMap = new HashMap<String, Map<WorkLevel, Map<String, String>>>();
    projectStatusNames.forEach(projectStatusName -> {
      Map<WorkLevel, Map<String, String>> levelEditRules = new HashMap<WorkLevel, Map<String, String>>();
      for (WorkLevel workLevel : WorkLevel.values()) {
        Map<String, String> fieldPermissions = new HashMap<String, String>();
        workflowEditRules.stream().filter(workflowEditRule ->
              workLevel.name().equals(workflowEditRule.getLevel()) && projectStatusName.equals(workflowEditRule.getProjectStatus())
              ).forEach(workflowEditRule ->
                    fieldPermissions.put(workflowEditRule.getField(), workflowEditRule.getNormalizedPermission())
              );
        levelEditRules.put(workLevel, fieldPermissions);
      }
      workflowEditRulesMap.put(projectStatusName, levelEditRules);
    });

    return workflowEditRulesMap;
  }
}
