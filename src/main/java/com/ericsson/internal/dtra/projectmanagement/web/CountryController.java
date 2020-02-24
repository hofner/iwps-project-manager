package com.ericsson.internal.dtra.projectmanagement.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;
import com.ericsson.internal.dtra.projectmanagement.domain.view.CountryView;
import com.ericsson.internal.dtra.projectmanagement.service.CountryRetriever;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@Api(tags = { "v1", "countries" })
@RequestMapping(value = { "/v1/countries" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

  @Autowired
  private CountryRetriever countryRetriever;

  @RequestMapping(method = RequestMethod.GET)
  @JsonView(CountryView.class)
  @ApiOperation(value = "Returns the list of country names based on wanted region", response = Country.class)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<List<Country>> getCountriesByRegion(
        @ApiParam(name = "region Id", value = "RegionId under which the project is") @RequestParam Integer regionId) {
    return new ResponseEntity<>(countryRetriever.getCountriesByRegion(regionId), HttpStatus.OK);
  }

  @RequestMapping(value = "/time_zones", method = RequestMethod.GET)
  @ApiOperation(value = "Returns a list of all the time zone", response = String[].class)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<List<String>> getTimeZones() {
    return new ResponseEntity<>(countryRetriever.getAllTimeZones(), HttpStatus.OK);
  }

  @RequestMapping(value = "/names", method = RequestMethod.GET)
  @ApiOperation(value = "Returns the list of country name", response = String[].class)
  @PermissionRequired(DTRAPermission.PROJECT_VIEW)
  public ResponseEntity<List<String>> getCountryNames() {
    return new ResponseEntity<>(countryRetriever.getAllCountryNames(), HttpStatus.OK);
  }

}
