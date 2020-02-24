package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Region;

public class CountryRegionDataProvider {

  public static Country getMyanmar() {
    return new Country("Myanmar","UTC+06:30",false);
  }

  public static Region getRASO() {
    return new Region("RASO");
  }
}
