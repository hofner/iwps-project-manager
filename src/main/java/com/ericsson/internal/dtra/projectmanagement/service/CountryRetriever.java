package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.CountryRepository;

@Service
public class CountryRetriever {

  @Autowired
  private CountryRepository countryRepository;

  /**
  *
  * @param regionId, Ericsson's region
  * @return all the country names based on a wanted regionId
  */
  public List<Country> getCountriesByRegion(Integer regionId) {
    return countryRepository.findByRegionId(regionId);
  }

  /**
   * @return all the time zone names
   */
  public List<String> getAllTimeZones() {
    return countryRepository.findAllTimeZones();
  }

  /**
   * @return all the country names
   */
  public List<String> getAllCountryNames() {
    return countryRepository.findAllCountryNames();
  }
}
