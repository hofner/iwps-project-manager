package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackageStatusEvent;

@Repository
public interface WorkPackageStatusEventRepository extends JpaRepository<WorkPackageStatusEvent, Integer> {

  /**
   * Get the list of status event for a work package
   * @param workPackageId the work package id
   * @return list of work package status event for the requested work package
   */
  List<WorkPackageStatusEvent> findByWorkPackageId(Integer workPackageId);
}
