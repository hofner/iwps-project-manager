package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectDetailView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.WorkBreakdownStructureView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "work_breakdown_structure")
public class WorkBreakdownStructure extends StatefulAbstractAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(WorkBreakdownStructureView.class)
  private Integer id;

  @Column(nullable = false)
  @JsonView(WorkBreakdownStructureView.class)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workBreakdownStructure", cascade = CascadeType.ALL)
  private List<WorkBreakdownStructureStatusEvent> statusEvents;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  @JsonBackReference
  private Project project;

  @JsonView(WorkBreakdownStructureView.class)
  private String label;

  @JsonView(WorkBreakdownStructureView.class)
  private Long subNetwork;

  @JsonView(WorkBreakdownStructureView.class)
  private Date startDate;

  @JsonView(WorkBreakdownStructureView.class)
  private Date dueDate;

  @JsonView(WorkBreakdownStructureView.class)
  private String comments;

  @JsonView(WorkBreakdownStructureView.class)
  private String site;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workBreakdownStructure", cascade = CascadeType.ALL)
  @JsonManagedReference
  @JsonView(ProjectDetailView.class)
  private List<WorkPackage> workPackages;

  @JsonView(WorkBreakdownStructureView.class)
  private String technicalInput;

  @JsonView(WorkBreakdownStructureView.class)
  private String technicalOutput;

  @JsonView(WorkBreakdownStructureView.class)
  private String purchaseOrder;

  @JsonView(WorkBreakdownStructureView.class)
  private String networkActivity;

  @JsonView(WorkBreakdownStructureView.class)
  private String operationalActivity;

  private WorkBreakdownStructure(WorkBreakdownStructureBuilder builder) {
    this.operationalActivity = builder.operationalActivity;
    this.networkActivity = builder.networkActivity;
    this.purchaseOrder = builder.purchaseOrder;
    this.technicalInput = builder.technicalInput;
    this.technicalOutput = builder.technicalOutput;
    this.workPackages = builder.workPackages;
    this.site = builder.site;
    this.comments = builder.comments;
    this.dueDate = builder.dueDate;
    this.startDate = builder.startDate;
    this.subNetwork = builder.subNetwork;
    this.label = builder.label;
    this.project = builder.project;
    this.name = builder.name;
    this.status = builder.status;
  }

  public static class WorkBreakdownStructureBuilder {
    private String operationalActivity;
    private String networkActivity;
    private String purchaseOrder;
    private String technicalOutput;
    private String technicalInput;
    private List<WorkPackage> workPackages;
    private String site;
    private String comments;
    private Date dueDate;
    private Date startDate;
    private Long subNetwork;
    private String label;
    private Project project;
    private String name;
    private String status;

    public WorkBreakdownStructureBuilder(final Project project, final String name) {
      this.project = project;
      this.name = name;
    }

    public WorkBreakdownStructureBuilder operationalActivity(final String operationalActivity) {
      this.operationalActivity = operationalActivity;
      return this;
    }

    public WorkBreakdownStructureBuilder networkActivity(final String networkActivity) {
      this.networkActivity = networkActivity;
      return this;
    }

    public WorkBreakdownStructureBuilder purchaseOrder(final String purchaseOrder) {
      this.purchaseOrder = purchaseOrder;
      return this;
    }

    public WorkBreakdownStructureBuilder technicalOutput(final String technicalOutput) {
      this.technicalOutput = technicalOutput;
      return this;
    }

    public WorkBreakdownStructureBuilder technicalInput(final String technicalInput) {
      this.technicalInput = technicalInput;
      return this;
    }

    public WorkBreakdownStructureBuilder workPackages(final List<WorkPackage> workPackages) {
      this.workPackages = workPackages;
      return this;
    }

    public WorkBreakdownStructureBuilder site(final String site) {
      this.site = site;
      return this;
    }

    public WorkBreakdownStructureBuilder comments(final String comments) {
      this.comments = comments;
      return this;
    }

    public WorkBreakdownStructureBuilder dueDate(final Date dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public WorkBreakdownStructureBuilder startDate(final Date startDate) {
      this.startDate = startDate;
      return this;
    }

    public WorkBreakdownStructureBuilder subNetwork(final Long subNetwork) {
      this.subNetwork = subNetwork;
      return this;
    }

    public WorkBreakdownStructureBuilder label(final String label) {
      this.label = label;
      return this;
    }

    public WorkBreakdownStructureBuilder status(final String status) {
      this.status = status;
      return this;
    }

    public WorkBreakdownStructure build() {
      return new WorkBreakdownStructure(this);
    }
  }

  public WorkBreakdownStructure() {
    //default
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<WorkBreakdownStructureStatusEvent> getStatusEvents() {
    return statusEvents;
  }

  public void setStatusEvents(List<WorkBreakdownStructureStatusEvent> statusEvents) {
    this.statusEvents = statusEvents;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(final Project project) {
    this.project = project;
  }

  public Long getSubNetwork() {
    return subNetwork;
  }

  public void setSubNetwork(final Long subNetwork) {
    this.subNetwork = subNetwork;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(final Date startDate) {
    this.startDate = startDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(final Date dueDate) {
    this.dueDate = dueDate;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(final String comments) {
    this.comments = comments;
  }

  public String getSite() {
    return site;
  }

  public void setSite(final String site) {
    this.site = site;
  }

  public List<WorkPackage> getWorkPackages() {
    return workPackages;
  }

  public void setWorkPackages(final List<WorkPackage> workPackages) {
    this.workPackages = workPackages;
  }

  public String getTechnicalInput() {
    return technicalInput;
  }

  public void setTechnicalInput(final String technicalInput) {
    this.technicalInput = technicalInput;
  }

  public String getTechnicalOutput() {
    return technicalOutput;
  }

  public void setTechnicalOutput(final String technicalOutput) {
    this.technicalOutput = technicalOutput;
  }

  public String getPurchaseOrder() {
    return purchaseOrder;
  }

  public void setPurchaseOrder(final String purchaseOrder) {
    this.purchaseOrder = purchaseOrder;
  }

  public String getNetworkActivity() {
    return networkActivity;
  }

  public void setNetworkActivity(final String networkActivity) {
    this.networkActivity = networkActivity;
  }

  public String getOperationalActivity() {
    return operationalActivity;
  }

  public void setOperationalActivity(final String operationalActivity) {
    this.operationalActivity = operationalActivity;
  }
}
