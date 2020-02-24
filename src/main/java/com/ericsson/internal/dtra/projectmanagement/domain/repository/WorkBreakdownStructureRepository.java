package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;

@Repository
public interface WorkBreakdownStructureRepository extends JpaRepository<WorkBreakdownStructure, Integer> {

  /**
   * Gets a work breakdown structure
   * @param projectId: Id of project
   * @param id: Id of work break down structure
   * @return wanted work breakdown structure
   */
  public WorkBreakdownStructure findByProjectIdAndId(final Integer projectId, final Integer id);

  @Modifying
  public void deleteByIdIn(final Integer[] ids);

}
