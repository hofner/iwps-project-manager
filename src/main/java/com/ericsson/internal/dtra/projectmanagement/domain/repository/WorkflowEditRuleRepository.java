package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;

@Repository
public interface WorkflowEditRuleRepository extends JpaRepository<WorkflowEditRule, Integer> {

  WorkflowEditRule findOneByProjectStatusAndFieldAndLevel(String projectStatus, String field, String level);

}
