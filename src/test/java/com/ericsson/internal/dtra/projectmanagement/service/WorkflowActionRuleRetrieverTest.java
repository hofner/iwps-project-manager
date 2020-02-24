package com.ericsson.internal.dtra.projectmanagement.service;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ericsson.internal.dtra.projectmanagement.dataprovider.WorkflowDataProvider;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.dto.WorkflowAction;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;

@Test
public class WorkflowActionRuleRetrieverTest {

  @InjectMocks
  private WorkflowActionRuleRetriever workflowActionRuleRetriever;

  @Mock
  private WorkflowActionRuleRepository workflowActionRuleRepository;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(workflowActionRuleRepository.findAll()).thenReturn(WorkflowDataProvider.getWorkflowActionRules());
  }

  public void buildWorkflowActionRulesMap_validStaticData_completeMap() {
    Map<WorkLevel, Map<String, Collection<WorkflowAction>>> workflowActionsMap = workflowActionRuleRetriever.buildWorkflowActionRulesMap();
    // Validate that we get possible actions for all Work Level
    for (WorkLevel workLevel : WorkLevel.values()) {
      assertNotNull(workflowActionsMap.get(workLevel));
    }
    // Validate the workflow action rules for a Project in Preparation state
    assertTrue(validateWorkflowActions(workflowActionsMap.get(WorkLevel.PROJECT).get(StatusEnum.PREPARATION.getStatus()),
          new WorkflowAction("Planning Phoenix", StatusEnum.PLANNED.getStatus(), "PERMISSION1"), 1),
          "Error validating workflow action rules for a Project in Preparation state");
    // Validate the workflow action rules for a Project in Planned state
    assertTrue(validateWorkflowActions(workflowActionsMap.get(WorkLevel.PROJECT).get(StatusEnum.PLANNED.getStatus()),
          new WorkflowAction("Replanning Phoenix after 3 months", StatusEnum.CANCELLED.getStatus(), "PERMISSION2"), 2),
          "Error validating workflow action rules for a Project in Preparation state");
    // Validate the workflow action rules for a WBS in Demand Submitted state
    assertTrue(validateWorkflowActions(workflowActionsMap.get(WorkLevel.WBS).get(StatusEnum.DEMAND_SUBMITTED.getStatus()),
          new WorkflowAction("Serious action data", StatusEnum.CANCELLED.getStatus(), "PERMISSION6"), 1),
          "Error validating workflow action rules for a Project in Preparation state");
    // Validate that no workflow action rules can be found for a Project in Demand Submitted state
    assertNull(workflowActionsMap.get(WorkLevel.PROJECT).get(StatusEnum.DEMAND_SUBMITTED.getStatus()),
          "Expected to find no workflow rules for a project in Demand Submitted state");
  }

  /**
   * Checks that the workflow actions are not null, run assertions on the size and check that the list contains at least one given WorkflowAction
   * @param workflowActions List of workflow actions
   * @param expectedWorkflowAction The expected workflow action to be found in the list
   * @param expectedSize Expected size for the validated list
   * @return true if the workflowActions contain the expected workflowAction
   */
  private Boolean validateWorkflowActions(Collection<WorkflowAction> workflowActions, WorkflowAction expectedWorkflowAction, int expectedSize) {
    assertNotNull(workflowActions, "Expected to find workflow action " + expectedWorkflowAction.getAction() + "in the workflow rules map but found a null list");
    assertEquals(workflowActions.size(), expectedSize, "Error asserting the size of a workflow actions list in the workflow rules map");
    for (WorkflowAction workflowAction : workflowActions) {
      if (workflowAction.getAction().equals(expectedWorkflowAction.getAction()) && workflowAction.getNextStatus().equals(expectedWorkflowAction.getNextStatus())
            && workflowAction.getNormalizedPermission().equals(expectedWorkflowAction.getNormalizedPermission())) {
        return true;
      }
    }
    return false;
  }

}
