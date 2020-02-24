package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import java.util.Arrays;
import java.util.List;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;

public class WorkflowDataProvider {

  public static List<WorkflowActionRule> getWorkflowActionRules() {
    return Arrays
          .asList(
                new WorkflowActionRule(StatusEnum.PREPARATION.getStatus(), StatusEnum.PLANNED.getStatus(), "Planning Phoenix",
                      WorkLevel.PROJECT.name(), "permission1", "PERMISSION1"),
                new WorkflowActionRule(StatusEnum.PLANNED.getStatus(), StatusEnum.CANCELLED.getStatus(), "Replanning Phoenix after 3 months",
                      WorkLevel.PROJECT.name(), "permission2", "PERMISSION2"),
                new WorkflowActionRule(StatusEnum.PLANNED.getStatus(), StatusEnum.PREPARATION.getStatus(), "No User stories are closed...",
                      WorkLevel.PROJECT.name(), "permission3", "PERMISSION3"),
                new WorkflowActionRule(StatusEnum.PLANNED.getStatus(), StatusEnum.DEMAND_SUBMITTED.getStatus(), "AUTOMATIC",
                      WorkLevel.PROJECT.name(), "permission4", "PERMISSION4"),
                new WorkflowActionRule(StatusEnum.PLANNED.getStatus(), StatusEnum.DEMAND_SUBMITTED.getStatus(),
                      "Phase 2 starting already?!", WorkLevel.WBS.name(), "permission5", "PERMISSION5"),
                new WorkflowActionRule(StatusEnum.DEMAND_SUBMITTED.getStatus(), StatusEnum.CANCELLED.getStatus(),
                      "Serious action data", WorkLevel.WBS.name(), "permission6", "PERMISSION6"),
                new WorkflowActionRule(StatusEnum.PREPARATION.getStatus(), StatusEnum.PLANNED.getStatus(), "Add 130000 WP",
                      WorkLevel.WP.name(), "permission7", "PERMISSION7"),
                new WorkflowActionRule(StatusEnum.PLANNED.getStatus(), StatusEnum.CANCELLED.getStatus(),
                      "Takes 5 minutes to load them D:", WorkLevel.WP.name(), "permission8", "PERMISSION8"));
  }

  public static List<WorkflowEditRule> getWorkflowEditRules() {
    return Arrays.asList(new WorkflowEditRule("quality", WorkLevel.PROJECT.name(), StatusEnum.PREPARATION.getStatus(), "permission1", "PERMISSION1"),
          new WorkflowEditRule("first", WorkLevel.PROJECT.name(), StatusEnum.PREPARATION.getStatus(), "permission2", "PERMISSION2"),
          new WorkflowEditRule("always", WorkLevel.PROJECT.name(), StatusEnum.PREPARATION.getStatus(), "permission3", "PERMISSION3"),
          new WorkflowEditRule("quality", WorkLevel.PROJECT.name(), StatusEnum.PLANNED.getStatus(), "permission4", "PERMISSION4"),
          new WorkflowEditRule("soMuch", WorkLevel.WBS.name(), StatusEnum.PREPARATION.getStatus(), "permission5", "PERMISSION5"),
          new WorkflowEditRule("testing", WorkLevel.WBS.name(), StatusEnum.DEMAND_SUBMITTED.getStatus(), "permission6", "PERMISSION6"));
  }
}
