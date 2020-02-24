package com.ericsson.internal.dtra.projectmanagement.service;

import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ericsson.internal.dtra.projectmanagement.authorization.utils.SecurityContextUtils;
import com.ericsson.internal.dtra.projectmanagement.dataprovider.TestUserEnum;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.dataprovider.ProjectDataProvider;

@Test(groups = "unit")
public class ProjectRecorderTest {

  @InjectMocks
  private ProjectRecorder projectRecorder;

  @Mock
  private ProjectRepository projectRepository;

  @BeforeTest
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Test that when trying to create a project with a defined project ID, we throw
   * an integrity violation exception as it is a security concern.
   */
  @Test(expectedExceptions = DataIntegrityViolationException.class)
  public void createProject_nonNullId_throwDataIntegrityViolationException() {
    // Prepare it
    Project project = ProjectDataProvider.getProjectWithId();

    // Try it
    projectRecorder.createProject(project);
  }

  /**
   * Test that when creating a valid project, we set its status to Preparation
   */
  @Test
  public void createProject_validProject_setStatusToPreparation() {
    // Prepare it
    Project project = ProjectDataProvider.getProjectWithoutId();
    SecurityContextUtils.addUserToContext(TestUserEnum.CPM_USER);

    // Mock it
    Mockito.when(projectRepository.save(Mockito.any(Project.class))).then(AdditionalAnswers.returnsFirstArg());

    // Try it
    project = projectRecorder.createProject(project);

    // Validate it
    Assert.assertEquals(
          project.getStatus(),
          StatusEnum.PREPARATION.getStatus(),
          "The status at creation must be: Preparation");
  }

}
