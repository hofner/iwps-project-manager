package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;

@Service
public class StatusRetriever {

  @Autowired
  private WorkflowActionRuleRepository workflowActionRuleRepository;

  /**
   * This method returns list of status names for the wanted level that user is working on
   * @param workLevels List of the levels that user needs to get the status for them
   * @return list of status names
   */
  public Set<String> getStatusNames(List<WorkLevel> workLevels) {
    List<String> levelNames = workLevels.stream().map(workLevel -> workLevel.name()).collect(Collectors.toList());
    return getAllPossibleStatusNames(workflowActionRuleRepository.findAllByLevelIn(levelNames));
  }

  private Set<String> getAllPossibleStatusNames(List<WorkflowActionRule> workflows) {
    SortedSet<String> statusNames = new TreeSet<String>();
    workflows.forEach(workflow -> {
      statusNames.add(workflow.getCurrentStatus());
      statusNames.add(workflow.getNextStatus());
    });
    return statusNames;
  }
}