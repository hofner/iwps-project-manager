package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;

import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.google.common.collect.Sets;

public class StatusDataProvider {

  @DataProvider(name = "invalid project status to create wbs/wp")
  public static Object[][] invalidProjectStatusToCreateWorkBreakdownStructure() {
    return new Object[][] {
          { StatusEnum.PLANNED.getStatus() },
          { StatusEnum.PLAN_ACCEPTED.getStatus() },
          { StatusEnum.CANCELLED.getStatus() },
          { StatusEnum.CLOSED.getStatus() },
          { StatusEnum.DELIVERY_ACCEPTED.getStatus() },
          { StatusEnum.DEMAND_SUBMITTED.getStatus() },
          { StatusEnum.FULFILL_WP_ORDER.getStatus() }
    };
  }

  @DataProvider(name = "valid project status to create wbs/wp")
  public static Object[][] validProjectStatusToCreateWorkBreakdownStructure() {
    return new Object[][] {
          { StatusEnum.PLAN_SUBMITTED.getStatus() },
          { StatusEnum.PREPARATION.getStatus() }
    };
  }

  /**
   * Get a list of a all project status
   * @return List of all project status
   */
  public static Set<String> getProjectStatus() {
    Set<String> statuses = new HashSet<String>();
    statuses.addAll(Sets.newHashSet(StatusEnum.PREPARATION.getStatus(), StatusEnum.PLANNED.getStatus(),
          StatusEnum.PLAN_SUBMITTED.getStatus(), StatusEnum.PLAN_ACCEPTED.getStatus(), StatusEnum.FULFILL_WP_ORDER.getStatus(),
          StatusEnum.DEMAND_SUBMITTED.getStatus(), StatusEnum.DELIVERY_ACCEPTED.getStatus(), StatusEnum.CLOSED.getStatus(),
          StatusEnum.CANCELLED.getStatus(), StatusEnum.CR_SUBMITTED.getStatus(), StatusEnum.CR_PREPARATION.getStatus()));
    return statuses;
  }
}