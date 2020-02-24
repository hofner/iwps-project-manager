package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructureFieldChangeEvent;

@Repository
public interface WorkBreakdownStructureFieldChangeEventRepository
      extends JpaRepository<WorkBreakdownStructureFieldChangeEvent, Integer> {

  /**
   * Get the list of field change event for a work breakdown structure
   * @param workBreakdownStructureId the work breakdown structure id
   * @return list of WBS field change event for the requested WBS
   */
  List<WorkBreakdownStructureFieldChangeEvent> findByWorkBreakdownStructureId(Integer workBreakdownStructureId);
}
