package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;

public class WorkBreakdownStructureDataProvider {

  public static WorkBreakdownStructure getSimpleWorkBreakdownStructure(Integer id, String status) {
    WorkBreakdownStructure simpleWorkBreakdownStructure = new WorkBreakdownStructure();
    simpleWorkBreakdownStructure.setId(id);
    simpleWorkBreakdownStructure.setStatus(status);
    simpleWorkBreakdownStructure.setName("WBS-1");
    return simpleWorkBreakdownStructure;
  }

  public static List<WorkBreakdownStructure> getTwoWorkBreakdownStructures(final String projectStatus) {
    List<WorkBreakdownStructure> workBreakdownStructures = new ArrayList<>();
    String wbsStatus = StatusEnum.INITIALIZED.getStatus();

    WorkBreakdownStructure simpleWorkBreakdownStructureOne = getSimpleWorkBreakdownStructure(1, wbsStatus);
    workBreakdownStructures.add(simpleWorkBreakdownStructureOne);

    WorkBreakdownStructure simpleWorkBreakdownStructureTwo = getSimpleWorkBreakdownStructure(2, wbsStatus);
    workBreakdownStructures.add(simpleWorkBreakdownStructureTwo);
    return workBreakdownStructures;
  }
}
