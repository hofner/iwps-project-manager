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
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProductAreaView;
import com.ericsson.internal.dtra.projectmanagement.service.ProductAreaRetriever;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

@RestController
@Api(tags = { "v1", "product_areas" })
@RequestMapping(value = { "/v1/product_areas" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductAreaController {

  @Autowired
  private ProductAreaRetriever productAreaRetriever;

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Returns a list of all the product areas", response = ProductArea[].class)
  @JsonView(ProductAreaView.class)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<List<ProductArea>> getProductAreas() {
    return new ResponseEntity<>(productAreaRetriever.getAllProductAreas(), HttpStatus.OK);
  }

  @RequestMapping(value = "/names", method = RequestMethod.GET)
  @ApiOperation(value = "Returns a list of all the product area name", response = String[].class)
  @PermissionRequired(DTRAPermission.PROJECT_VIEW)
  public ResponseEntity<List<String>> getProductAreaNames() {
    return new ResponseEntity<>(productAreaRetriever.getAllProductAreaNames(), HttpStatus.OK);
  }

}
