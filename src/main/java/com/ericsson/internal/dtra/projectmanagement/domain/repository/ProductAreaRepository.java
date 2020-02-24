package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;


@Repository
public interface ProductAreaRepository extends JpaRepository<ProductArea, Integer> {

  /**
   * Get all the product area names
   * @return return all the product area names
   */
  @Query("SELECT DISTINCT productArea.name FROM ProductArea productArea ORDER BY name")
  List<String> findAllNames();

}
