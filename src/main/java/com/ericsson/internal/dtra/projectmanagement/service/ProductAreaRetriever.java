package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProductAreaRepository;

@Service
public class ProductAreaRetriever {

  @Autowired
  private ProductAreaRepository productAreaRepository;

  /**
   * @return all the product areas
   */
  public List<ProductArea> getAllProductAreas() {
    return productAreaRepository.findAll();
  }

  /**
   * @return all the product area names
   */
  public List<String> getAllProductAreaNames() {
    return productAreaRepository.findAllNames();
  }
}
