package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import java.util.LinkedList;
import java.util.List;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;

public class WorkPackageDataProvider {

  public static List<WorkPackage> getCompressedWorkPackagesForExistingWBS() {
    List<WorkPackage> workPackages = new LinkedList<>();

    WorkPackage workPackageOne = new WorkPackage.WorkPackageBuilder(
          WorkBreakdownStructureDataProvider.getSimpleWorkBreakdownStructure(1,
                StatusEnum.INITIALIZED.getStatus()),
          "WP-1", "1.1", null, "code-11").build();
    workPackageOne.setRequestedCount(5);
    workPackages.add(workPackageOne);

    WorkPackage workPackageTwo = new WorkPackage.WorkPackageBuilder(
          WorkBreakdownStructureDataProvider.getSimpleWorkBreakdownStructure(2,
                StatusEnum.INITIALIZED.getStatus()),
          "WP-2", "2.1", null, "code-22").build();
    workPackageTwo.setRequestedCount(2);
    workPackages.add(workPackageTwo);
    return workPackages;
  }

  public static List<WorkPackage> getCompressedWorkPackages() {
    List<WorkPackage> workPackages = new LinkedList<>();

    WorkPackage workPackageOne = new WorkPackage.WorkPackageBuilder(null, "WP-1", "1.1", null, "code-1").build();
    workPackageOne.setRequestedCount(3);
    workPackages.add(workPackageOne);

    WorkPackage workPackageTwo = new WorkPackage.WorkPackageBuilder(null, "WP-2", "2.1", null, "code-2").build();
    workPackageTwo.setRequestedCount(2);
    workPackages.add(workPackageTwo);
    return workPackages;
  }
}
