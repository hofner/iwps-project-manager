package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructureStatusEvent;

@Repository
public interface WorkBreakdownStructureStatusEventRepository extends JpaRepository<WorkBreakdownStructureStatusEvent, Integer> {

  /**
   * Get the list of status event for a work breakdown structure
   * @param workBreakdownStructureId the work breakdown structure id
   * @return list of WBS status event for the requested work breakdown structure
   */
  List<WorkBreakdownStructureStatusEvent> findByWorkBreakdownStructureId(Integer workBreakdownStructureId);
}
