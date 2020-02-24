package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;

@Repository
public interface WorkPackageRepository extends JpaRepository<WorkPackage, Integer> {

  public WorkPackage findByWorkBreakdownStructureIdAndId(final Integer workBreakdownStructureId, final Integer id);

  @Modifying
  public void deleteByIdIn(final Integer[] ids);

  public int countByWorkBreakdownStructureIdAndStatus(final int workBreakdownStructureId, final String status);

}
