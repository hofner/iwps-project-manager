package com.ericsson.internal.dtra.projectmanagement.service.dataprovider;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Region;

public class ProjectDataProvider {

  /**
   * Returns a {@link Project} with the following information,
   * - id: 666
   * - name: project with id
   * - timezone: UTC
   * - customer: curse tumor
   * - region:
   * - country
   * @return
   */
  public static Project getProjectWithId() {
    Project projectWithId = new Project(
          "project with id",
          "UTC",
          new Region(),
          new Country(),
          new ProductArea(),
          "curse tumor");
    projectWithId.setId(666);

    return projectWithId;
  }

  /**
   * Returns a {@link Project} with the following information,
   * - id: 666
   * - name: project without id
   * - timezone: UTC
   * - customer: curse tumor
   * - region:
   * - country
   * @return
   */
  public static Project getProjectWithoutId() {
    return new Project(
          "project without id",
          "UTC",
          new Region(),
          new Country(),
          new ProductArea(),
          "curse tumor");
  }

}
