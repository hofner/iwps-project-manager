package com.ericsson.internal.dtra.projectmanagement.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.service.WorkPackageChangeEventRetriever;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

@RestController
@Api(tags = { "v1", "work_packages" })
@RequestMapping(value = { "/v1/work_packages" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkPackageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkPackageController.class);

  @Autowired
  private WorkPackageChangeEventRetriever workPackageChangeEventRetriever;

  @ApiOperation(value = "Get a work package event history", response = ChangeEvent[].class)
  @RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
  @PermissionRequired(DTRAPermission.CHANGE_EVENT_VIEW)
  public ResponseEntity<List<ChangeEvent>> getWorkPackageStatusEvents(
        @ApiParam(name = "id", value = "Id of work package to fetch the event details") @PathVariable final Integer id) {
    return new ResponseEntity<>(workPackageChangeEventRetriever.getWorkPackageStatusEvents(id), HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  @PermissionRequired(DTRAPermission.AUTOMATIC_ACTION)
  public void udpateStatus(
        @ApiParam(name = "id", value = "Id of work package") @PathVariable final Integer id,
        @ApiParam(value = "Work Package with status field needs to be updated") @RequestBody final WorkPackage workPackage) {
    LOGGER.info("Work Package ID: "+ id + ", Work Package Status: "+ workPackage.getStatus());
    //TODO: Call service method to validate the status change request.
  }
}
