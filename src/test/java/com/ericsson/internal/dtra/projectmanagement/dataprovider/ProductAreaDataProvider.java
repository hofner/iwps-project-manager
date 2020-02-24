package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.CostModel;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;

public class ProductAreaDataProvider {

  public static ProductArea getCSI(){
    return new ProductArea("CSI", getActualCostModel());
  }

  public static CostModel getActualCostModel() {
    return new CostModel("Actual Cost Model");
  }
}
