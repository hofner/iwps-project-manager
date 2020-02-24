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
import com.ericsson.internal.dtra.projectmanagement.domain.entity.CostModel;
import com.ericsson.internal.dtra.projectmanagement.service.CostModelRetriever;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

@RestController
@Api(tags = { "v1", "cost_models" })
@RequestMapping(value = { "/v1/cost_models" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class CostModelController {

  @Autowired
  private CostModelRetriever costModelRetriever;

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Returns the list of cost models with related product areas", response = CostModel.class)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<List<CostModel>> getCostModels() {
    return new ResponseEntity<>(costModelRetriever.getAllCostModels(), HttpStatus.OK);
  }
}
