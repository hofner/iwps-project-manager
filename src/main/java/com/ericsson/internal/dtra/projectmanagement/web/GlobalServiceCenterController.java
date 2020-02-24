package com.ericsson.internal.dtra.projectmanagement.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.GlobalServiceCenter;
import com.ericsson.internal.dtra.projectmanagement.service.GlobalServiceCenterRetriever;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

@RestController
@Api(tags = { "v1", "gscs" })
@RequestMapping(value = { "/v1/gscs" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalServiceCenterController {

  @Autowired
  private GlobalServiceCenterRetriever globalServiceCenterRetriever;

  @RequestMapping(value = "/names", method = RequestMethod.GET)
  @ApiOperation(value = "Returns the list of GSC display name", response = String[].class)
  @PermissionRequired(DTRAPermission.PROJECT_VIEW)
  public ResponseEntity<List<String>> getGscNames() {
    return new ResponseEntity<>(globalServiceCenterRetriever.getAllGscDisplayNames(), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Returns the list of GSCs", response = GlobalServiceCenter[].class)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<List<GlobalServiceCenter>> getGSCs() {
    return new ResponseEntity<List<GlobalServiceCenter>>(globalServiceCenterRetriever.getAllGSCs(), HttpStatus.OK);
  }

}