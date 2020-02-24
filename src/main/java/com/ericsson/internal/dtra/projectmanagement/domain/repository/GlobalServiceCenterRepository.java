package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.GlobalServiceCenter;

@Repository
public interface GlobalServiceCenterRepository extends JpaRepository<GlobalServiceCenter, Integer> {

  /**
   * Get all the gsc display names
   * @return return all the gsc display names
   */
  @Query("SELECT gsc.displayName FROM GlobalServiceCenter gsc ORDER BY displayName")
  List<String> findAllDisplayNames();
}
