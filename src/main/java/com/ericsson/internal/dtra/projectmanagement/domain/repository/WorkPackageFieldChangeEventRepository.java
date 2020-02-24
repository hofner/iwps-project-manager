package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackageFieldChangeEvent;

@Repository
public interface WorkPackageFieldChangeEventRepository extends JpaRepository<WorkPackageFieldChangeEvent, Integer> {

  /**
   * Get the list of field change event for a work package
   * @param workPackageId the work package id
   * @return list of work package field change event for the requested work package
   */
  List<WorkPackageFieldChangeEvent> findByWorkPackageId(Integer workPackageId);
}
