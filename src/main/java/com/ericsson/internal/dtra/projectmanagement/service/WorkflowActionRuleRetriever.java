package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.dto.WorkflowAction;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

@Service
public class WorkflowActionRuleRetriever {

  private static final String AUTOMATIC_ACTION = "AUTOMATIC";

  @Autowired
  private WorkflowActionRuleRepository workflowActionRuleRepository;

  /**
   * Goes through all workflow action rules and builds a map to be used to display and allow actions based on current status
   * This map is From Current Work Level To Current Status To Possible Actions
   * @return A map of all workflow actions
   */
  public Map<WorkLevel, Map<String, Collection<WorkflowAction>>> buildWorkflowActionRulesMap() {
    Map<WorkLevel, Map<String, Collection<WorkflowAction>>> workflowActions = new HashMap<WorkLevel, Map<String, Collection<WorkflowAction>>>();

    List<WorkflowActionRule> actionRules = workflowActionRuleRepository.findAll();
    for (WorkLevel workLevel : WorkLevel.values()) {
      ListMultimap<String, WorkflowAction> workflowActionsByCurrentStatus = ArrayListMultimap.create();
      actionRules.stream().filter(workflow ->
            !AUTOMATIC_ACTION.equals(workflow.getAction()) && workLevel.name().equals(workflow.getLevel()))
            .forEach(actionRule -> workflowActionsByCurrentStatus.put(actionRule.getCurrentStatus(),
                  new WorkflowAction(actionRule.getAction(), actionRule.getNextStatus(), actionRule.getNormalizedPermission()))
            );
      workflowActions.put(workLevel, workflowActionsByCurrentStatus.asMap());
    }
    return workflowActions;
  }
}
