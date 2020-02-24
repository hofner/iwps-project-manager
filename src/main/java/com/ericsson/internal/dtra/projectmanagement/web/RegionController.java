package com.ericsson.internal.dtra.projectmanagement.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Region;
import com.ericsson.internal.dtra.projectmanagement.domain.view.RegionView;
import com.ericsson.internal.dtra.projectmanagement.service.RegionRetriever;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@Api(tags = { "v1", "regions" })
@RequestMapping(value = { "/v1/regions" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionController {

  @Autowired
  private RegionRetriever regionRetriever;

  @RequestMapping(method = RequestMethod.GET)
  @JsonView(RegionView.class)
  @ApiOperation(value = "Returns the list of regions", response = Region.class)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<List<Region>> getRegions() {
    return new ResponseEntity<>(regionRetriever.getAllRegions(), HttpStatus.OK);
  }

}
