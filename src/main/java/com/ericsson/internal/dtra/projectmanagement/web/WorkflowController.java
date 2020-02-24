package com.ericsson.internal.dtra.projectmanagement.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Set;

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
import com.ericsson.internal.dtra.projectmanagement.dto.WorkflowRules;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;
import com.ericsson.internal.dtra.projectmanagement.service.StatusRetriever;
import com.ericsson.internal.dtra.projectmanagement.service.WorkflowActionRuleRetriever;
import com.ericsson.internal.dtra.projectmanagement.service.WorkflowEditRuleRetriever;

@RestController
@Api(tags = { "v1", "workflows" })
@RequestMapping(value = { "/v1/workflows" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkflowController {

  @Autowired
  private StatusRetriever statusRetriever;

  @Autowired
  private WorkflowActionRuleRetriever workflowActionRuleRetriever;

  @Autowired
  private WorkflowEditRuleRetriever workflowEditRuleRetriever;

  @RequestMapping(value = "/status_names", method = RequestMethod.GET)
  @ApiOperation(value = "Returns the list of status names based on the level that user is working on", response = String[].class)
  @PermissionRequired(DTRAPermission.PROJECT_VIEW)
  public ResponseEntity<Set<String>> getProjectStatus(
        @ApiParam(name = "work level", value = "The level that user is working on. values can be a list of PROJECT, WBS, WP") @RequestParam(required = true) List<WorkLevel> workLevels)
  {
    return new ResponseEntity<>(statusRetriever.getStatusNames(workLevels), HttpStatus.OK);
  }

  @RequestMapping(value = "/rules", method = RequestMethod.GET)
  @ApiOperation(value = "Returns a map by work level and current status for action and edit workflow rules", response = String[].class)
  @PermissionRequired(DTRAPermission.PROJECT_VIEW)
  public ResponseEntity<WorkflowRules> getWorkflowRules() {
    return new ResponseEntity<>(new WorkflowRules(workflowActionRuleRetriever.buildWorkflowActionRulesMap(),
          workflowEditRuleRetriever.buildWorkflowEditRulesMap()), HttpStatus.OK);
  }
}