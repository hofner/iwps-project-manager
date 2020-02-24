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
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Bundling;
import com.ericsson.internal.dtra.projectmanagement.service.BundlingRetriever;

@RestController
@Api(tags = { "v1", "bundlings" })
@RequestMapping(value = { "/v1/bundlings" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class BundlingController {

  @Autowired
  private BundlingRetriever bundlingRetriever;

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Returns all the activity bundlings", response = Bundling[].class)
  @PermissionRequired(DTRAPermission.PROJECT_DETAILS_EDIT)
  public ResponseEntity<List<Bundling>> getActivityBundlings() {
    return new ResponseEntity<>(bundlingRetriever.getActivityBundlings(), HttpStatus.OK);
  }
}
