package com.ericsson.internal.dtra.projectmanagement.service;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ericsson.internal.dtra.projectmanagement.dataprovider.StatusDataProvider;
import com.ericsson.internal.dtra.projectmanagement.dataprovider.WorkflowDataProvider;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowEditRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;

@Test
public class WorkflowEditRuleRetrieverTest {

  @InjectMocks
  private WorkflowEditRuleRetriever workflowEditRuleRetriever;

  @Mock
  private WorkflowEditRuleRepository workflowEditRuleRepository;

  @Mock
  private StatusRetriever statusRetriever;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(workflowEditRuleRepository.findAll()).thenReturn(WorkflowDataProvider.getWorkflowEditRules());
    when(statusRetriever.getStatusNames(Arrays.asList(WorkLevel.PROJECT))).thenReturn(StatusDataProvider.getProjectStatus());
  }

  public void buildWorkflowEditRulesMap_validStaticData_completeMap() {
    Map<String, Map<WorkLevel, Map<String, String>>> workflowEditRules = workflowEditRuleRetriever.buildWorkflowEditRulesMap();
    assertNotNull(workflowEditRules, "Received a null map for workflow edit rules");

    // Validate the editable fields for a Project in Preparation state
    Map<WorkLevel, Map<String, String>> editableFieldsProjectPreparation = workflowEditRules.get(StatusEnum.PREPARATION.getStatus());
    assertNotNull(editableFieldsProjectPreparation, "Expected to find editable fields for a Project in Preparation, but found null");
    Map<String, String> projectFieldsPreparation = editableFieldsProjectPreparation.get(WorkLevel.PROJECT);
    assertNotNull(projectFieldsPreparation, "Expected to find editable Project fields for a Project in Preparation, but found null");
    assertEquals(projectFieldsPreparation.get("quality"), "PERMISSION1", "Unexpected normalized permission found for field Project.quality with Project in Preparaton");
    assertEquals(projectFieldsPreparation.get("first"), "PERMISSION2", "Unexpected normalizd permission found for field Project.first with Project in Preparation");
    assertEquals(projectFieldsPreparation.get("always"), "PERMISSION3", "Unexpected normalized permission found for field Project.always with Project in Preparation");
    Map<String, String> wbsFieldsPreparation = editableFieldsProjectPreparation.get(WorkLevel.WBS);
    assertNotNull(wbsFieldsPreparation, "Expected to find editable WBS fields for a Project in Preparation, but found null");
    assertEquals(wbsFieldsPreparation.get("soMuch"), "PERMISSION5", "Unexpected normalized permission found for field WBS.soMuch with Project in Preparation");
    Map<String, String> wpFieldsPreparation = editableFieldsProjectPreparation.get(WorkLevel.WP);
    assertTrue(wpFieldsPreparation.isEmpty(), "Expected to find no editable WP fields for a Project in Preparation, but found an empty list");

  }
}
