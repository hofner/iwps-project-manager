package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;

@Repository
public interface WorkflowActionRuleRepository extends JpaRepository<WorkflowActionRule, Integer> {

  /**
   * Find all workflow rules by work level
   * @param levelNames names of required work levels
   * @return List of workflow objects
   */
  List<WorkflowActionRule> findAllByLevelIn(List<String> levelNames);

  WorkflowActionRule findOneByCurrentStatusAndNextStatusAndLevel(String currentStatus, String nextStatus, String level);

  // Since it is available only at spring-jpa 1.11 (we are at 1.10.6), we use a native query for now
  @Query("select count(rule)>0 from WorkflowActionRule rule "
        + "where rule.currentStatus = :currentStatus and "
        + "rule.nextStatus = :nextStatus and "
        + "rule.action = :action")
  boolean existsByCurrentStatusAndNextStatusAndAction(
        @Param("currentStatus") final String currentStatus,
        @Param("nextStatus") final String nextStatus,
        @Param("action") final String action);
}
