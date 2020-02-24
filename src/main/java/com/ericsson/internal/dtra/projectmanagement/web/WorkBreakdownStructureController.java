package com.ericsson.internal.dtra.projectmanagement.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.service.WorkBreakdownStructureChangeEventRetriever;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

@RestController
@Api(tags = { "v1", "work_breakdown_structures" })
@RequestMapping(value = { "/v1/work_breakdown_structures" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkBreakdownStructureController {

  @Autowired
  private WorkBreakdownStructureChangeEventRetriever workBreakdownStructureChangeEventRetriever;

  @ApiOperation(value = "Get a work breakdown structure events history", response = ChangeEvent[].class)
  @RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
  @PermissionRequired(DTRAPermission.CHANGE_EVENT_VIEW)
  public ResponseEntity<List<ChangeEvent>> getProjectStatusEvents(
        @ApiParam(name = "id", value = "Id of work breakdown structure to fetch the events") @PathVariable final Integer id) {
    return new ResponseEntity<>(
          workBreakdownStructureChangeEventRetriever.getWorkBreakdownStructureStatusEvents(id),
          HttpStatus.OK);
  }
}
