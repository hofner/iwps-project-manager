package com.ericsson.internal.dtra.projectmanagement.service;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataRetrievalFailureException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ericsson.internal.dtra.projectmanagement.dataprovider.ProjectDataProvider;
import com.ericsson.internal.dtra.projectmanagement.dataprovider.StatusDataProvider;
import com.ericsson.internal.dtra.projectmanagement.dataprovider.WorkBreakdownStructureDataProvider;
import com.ericsson.internal.dtra.projectmanagement.dataprovider.WorkPackageDataProvider;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;

@Test(groups = "unit")
public class WorkPackageRecorderTest {

  @InjectMocks
  private WorkPackageRecorder workPackageRecorder;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private WorkPackageRepository workPackageRepository;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expectedExceptions = DataRetrievalFailureException.class)
  public void createWorkPackages_OnNoneExistingProject_DataRetrievalFailureException() {

    final Integer nonExistingProjectId = 1000;
    final Integer workBreakdownStructureId = 1;
    Mockito.when(projectRepository.findOne(nonExistingProjectId)).thenReturn(null);
    //call
    workPackageRecorder.createWorkPackages(nonExistingProjectId,
          workBreakdownStructureId,
          WorkPackageDataProvider.getCompressedWorkPackagesForExistingWBS());
  }

  @Test(expectedExceptions = DataRetrievalFailureException.class)
  public void createWorkPackages_OnNoneExistingWorkBreakdownOfProject_DataRetrievalFailureException() {

    final Project existingProject = ProjectDataProvider
          .getProjectWithTwoWorkBreakdownStructures(StatusEnum.PREPARATION.getStatus());
    final Integer existingProjectId = existingProject.getId();
    final Integer noneExistingWorkBreakdownStructureId = 1000;

    Mockito.when(projectRepository.findOne(existingProjectId)).thenReturn(existingProject);
    //call
    workPackageRecorder.createWorkPackages(existingProjectId,
          noneExistingWorkBreakdownStructureId,
          WorkPackageDataProvider.getCompressedWorkPackagesForExistingWBS());
  }

  @Test(dataProvider = "invalid project status to create wbs/wp", dataProviderClass = StatusDataProvider.class, expectedExceptions = BadRequestException.class)
  public void createWorkPackages_OnProjectWithInvalidStatus_BadRequestException(String invalidStatus) {

    final Integer existingProjectId = ProjectDataProvider.getProjectWithTwoWorkBreakdownStructures(invalidStatus)
          .getId();
    final Integer existingWorkBreakdownStructure = 1;

    Project projectWithInvalidStatus = ProjectDataProvider.getProjectWithTwoWorkBreakdownStructures(invalidStatus);

    Mockito.when(projectRepository.findOne(existingProjectId)).thenReturn(projectWithInvalidStatus);
    //call
    workPackageRecorder.createWorkPackages(existingProjectId,
          existingWorkBreakdownStructure,
          WorkPackageDataProvider.getCompressedWorkPackagesForExistingWBS());
  }

  public void uncompressWorkPackages_FromCompressedWorkPackagesList_ListOfUncompressedWorkPackages() {

    int expectedWorkPackagesListSize = WorkPackageDataProvider.getCompressedWorkPackagesForExistingWBS().stream()
          .map(cwp -> cwp.getRequestedCount()).mapToInt(i -> i.intValue()).sum();
    //call
    List<WorkPackage> uncompressedWorkPackages = workPackageRecorder.uncompressWorkPackages(WorkPackageDataProvider
          .getCompressedWorkPackagesForExistingWBS(),
          WorkBreakdownStructureDataProvider.getSimpleWorkBreakdownStructure(1,
                StatusEnum.INITIALIZED.getStatus()));
    Assert.assertEquals(uncompressedWorkPackages.size(), expectedWorkPackagesListSize);
  }

  @Test(dataProvider = "valid project status to create wbs/wp", dataProviderClass = StatusDataProvider.class)
  public void createWorkPackages_OnProjectWithValidStatus_SavedSuccessfully(String validStatus) {

    Project projectWithValidStatus = ProjectDataProvider.getProjectWithTwoWorkBreakdownStructures(validStatus);
    final Integer existingProjectId = projectWithValidStatus.getId();
    final Integer existingWorkBreakdownStructure = projectWithValidStatus.getWorkBreakdownStructures().get(0).getId();

    Mockito.reset(workPackageRepository);
    Mockito.when(projectRepository.findOne(existingProjectId)).thenReturn(projectWithValidStatus);
    //call
    workPackageRecorder.createWorkPackages(existingProjectId,
          existingWorkBreakdownStructure,
          WorkPackageDataProvider.getCompressedWorkPackagesForExistingWBS());

    verify(workPackageRepository).save(anyListOf(WorkPackage.class));
  }
}
