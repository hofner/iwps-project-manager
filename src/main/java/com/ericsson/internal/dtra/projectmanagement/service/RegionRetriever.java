package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Region;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.RegionRepository;

@Service
public class RegionRetriever {

  @Autowired
  private RegionRepository regionRepository;

  public List<Region> getAllRegions() {

    return regionRepository.findAll();
  }
}
