package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;


@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

  /**
   * Get all the time zone names
   * @return return all the time zone names
   */
  @Query("SELECT DISTINCT country.timezone FROM Country country ORDER BY timezone")
  List<String> findAllTimeZones();

  /**
   *
   * @param regionId: Id for Ericsson regions
   * @return List of countries based on a regionId
   */
  List<Country> findByRegionId(Integer regionId);

  /**
   * Get all the country names
   * @return return all the country names
   */
  @Query("SELECT country.name FROM Country country ORDER BY name")
  List<String> findAllCountryNames();
}
