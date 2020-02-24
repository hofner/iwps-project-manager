package com.ericsson.internal.dtra.projectmanagement.dataprovider;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;

public class ProjectDataProvider {

  public static Project getBasicProject(final String status) {
    Project basicProject = new Project("Basic-Project",
          "UTC+06:30",
          CountryRegionDataProvider.getRASO(),
          CountryRegionDataProvider.getMyanmar(),
          ProductAreaDataProvider.getCSI(),
          "Customer-Myanmar");
    basicProject.setId(1);
    basicProject.setStatus(status);
    return basicProject;
  }

  public static Project getProjectWithTwoWorkBreakdownStructures(String status) {
    Project project = getBasicProject(status);
    project.setWorkBreakdownStructures(WorkBreakdownStructureDataProvider.getTwoWorkBreakdownStructures(status));
    return project;
  }

}
