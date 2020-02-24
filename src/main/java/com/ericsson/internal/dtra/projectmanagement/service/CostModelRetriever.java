package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.CostModel;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.CostModelRepository;

@Service
public class CostModelRetriever {

  @Autowired
  private CostModelRepository costModelRepository;

  /**
   *
   * @return all the cost models with related product areas
   */
  public List<CostModel> getAllCostModels() {
    return costModelRepository.findAll();
  }

}
