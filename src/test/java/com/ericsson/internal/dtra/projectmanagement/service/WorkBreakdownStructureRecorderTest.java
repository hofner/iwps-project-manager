package com.ericsson.internal.dtra.projectmanagement.service;

import static org.mockito.Mockito.verify;

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
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;

@Test(groups = "unit")
public class WorkBreakdownStructureRecorderTest {

  @InjectMocks
  private WorkBreakdownStructureRecorder workBreakdownStructureRecorder;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private WorkBreakdownStructureRepository workBreakdownStructureRepository;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expectedExceptions = DataRetrievalFailureException.class)
  public void createWorkBreakdownStructure_OnNoneExistingProject_DataRetrievalFailureException() {

    final Integer nonExistingProjectId = 1000;
    final WorkBreakdownStructure workBreakdownStructure = WorkBreakdownStructureDataProvider
          .getSimpleWorkBreakdownStructure(1, null);
    Mockito.when(projectRepository.findOne(nonExistingProjectId)).thenReturn(null);
    //call
    workBreakdownStructureRecorder.createWorkBreakdownStructure(nonExistingProjectId,
          workBreakdownStructure);
  }

  @Test(dataProvider = "invalid project status to create wbs/wp", dataProviderClass = StatusDataProvider.class, expectedExceptions = BadRequestException.class)
  public void createWorkBreakdownStructure_OnProjectWithInvalidStatus_BadRequestException(String invalidStatus) {

    final Project projectWithInvalidStatus = ProjectDataProvider
          .getProjectWithTwoWorkBreakdownStructures(invalidStatus);
    final Integer existingProjectId = projectWithInvalidStatus.getId();
    final WorkBreakdownStructure workBreakdownStructureTobeCreated = WorkBreakdownStructureDataProvider
          .getSimpleWorkBreakdownStructure(1, null);
    Mockito.when(projectRepository.findOne(existingProjectId)).thenReturn(projectWithInvalidStatus);
    //call
    workBreakdownStructureRecorder.createWorkBreakdownStructure(existingProjectId, workBreakdownStructureTobeCreated);
  }

  @Test(enabled = true, dataProvider = "valid project status to create wbs/wp", dataProviderClass = StatusDataProvider.class)
  public void createWorkBreakdownStructure_OnProjectWithValidStatus_SavedSuccessfully(String validStatus) {

    Project projectWithValidStatus = ProjectDataProvider.getBasicProject(validStatus);
    final Integer existingProjectId = projectWithValidStatus.getId();
    final WorkBreakdownStructure workBreakdownStructure = WorkBreakdownStructureDataProvider
          .getSimpleWorkBreakdownStructure(null,
                validStatus);
    workBreakdownStructure.setWorkPackages(WorkPackageDataProvider.getCompressedWorkPackages());
    Mockito.reset(workBreakdownStructureRepository);
    Mockito.when(projectRepository.findOne(existingProjectId)).thenReturn(projectWithValidStatus);
    //call
    workBreakdownStructureRecorder.createWorkBreakdownStructure(existingProjectId, workBreakdownStructure);
    verify(workBreakdownStructureRepository).save(workBreakdownStructure);
  }

  public void uncompressWorkPackages_CompressedWorkPackagesOnWorkBreakdownStructure_WorkBreakdownStructureWithUncompressedWorkPackages() {

    int expectedWorkPackagesListSize = WorkPackageDataProvider.getCompressedWorkPackages().stream()
          .map(cwp -> cwp.getRequestedCount()).mapToInt(i -> i.intValue()).sum();

    final String validStatus = StatusEnum.INITIALIZED.getStatus();
    WorkBreakdownStructure workBreakdownStructure = WorkBreakdownStructureDataProvider
          .getSimpleWorkBreakdownStructure(null, validStatus);
    workBreakdownStructure.setWorkPackages(WorkPackageDataProvider.getCompressedWorkPackages());
    //call
    workBreakdownStructureRecorder.uncompressWorkPackages(workBreakdownStructure);
    Assert.assertEquals(workBreakdownStructure.getWorkPackages().size(), expectedWorkPackagesListSize);
  }
}
