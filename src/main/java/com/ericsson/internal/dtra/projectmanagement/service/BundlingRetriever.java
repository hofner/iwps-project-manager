package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Bundling;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.BundlingRepository;

@Service
public class BundlingRetriever {

  @Autowired
  private BundlingRepository bundlingRepository;

  /**
  *
  * @return all the activity bundling
  */
  public List<Bundling> getActivityBundlings() {
    return bundlingRepository.findAll();
  }
}
