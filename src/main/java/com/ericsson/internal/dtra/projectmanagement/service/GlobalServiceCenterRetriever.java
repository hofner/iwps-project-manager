package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.GlobalServiceCenter;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.GlobalServiceCenterRepository;

@Service
public class GlobalServiceCenterRetriever {

  @Autowired
  private GlobalServiceCenterRepository globalServiceCenterRepository;

  /**
   * @return a list of all the GSC display name
   */
  public List<String> getAllGscDisplayNames() {
    return globalServiceCenterRepository.findAllDisplayNames();
  }

  /**
   * @return a list of all the GSCs
   */
  public List<GlobalServiceCenter> getAllGSCs() {
    return globalServiceCenterRepository.findAll();
  }

}
