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
import javax.persistence.Transient;

import com.ericsson.internal.dtra.projectmanagement.domain.view.WorkPackageView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "work_package")
public class WorkPackage extends StatefulAbstractAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(WorkPackageView.class)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "work_breakdown_structure_id", nullable = false)
  @JsonBackReference
  private WorkBreakdownStructure workBreakdownStructure;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workPackage", cascade = CascadeType.ALL)
  private List<WorkPackageStatusEvent> statusEvents;

  //industrialized_work_package_code
  @Column(name = "industrialized_work_package_code")
  @JsonView(WorkPackageView.class)
  private String code;

  @Column(nullable = false)
  @JsonView(WorkPackageView.class)
  private String name;

  @Column(name = "industrialized_work_package_version", nullable = false)
  @JsonView(WorkPackageView.class)
  private String version;

  @JsonView(WorkPackageView.class)
  private Date startDate;

  @JsonView(WorkPackageView.class)
  private Date dueDate;

  @JsonView(WorkPackageView.class)
  private String comments;

  @JsonView(WorkPackageView.class)
  private String technicalInput;

  @JsonView(WorkPackageView.class)
  private String technicalOutput;

  @JsonView(WorkPackageView.class)
  private String purchaseOrder;

  @JsonView(WorkPackageView.class)
  private String serviceOrder;

  @JsonView(WorkPackageView.class)
  private String operationalActivity;

  @JsonView(WorkPackageView.class)
  private String networkActivity;

  @Transient
  private Integer requestedCount;

  private WorkPackage() {
  }

  private WorkPackage(WorkPackageBuilder builder) {

    this.workBreakdownStructure = builder.workBreakdownStructure;
    this.name = builder.name;
    this.version = builder.version;
    this.status = builder.status;
    this.code = builder.code;
    this.networkActivity = builder.networkActivity;
    this.operationalActivity = builder.operationalActivity;
    this.purchaseOrder = builder.purchaseOrder;
    this.technicalInput = builder.technicalInput;
    this.technicalOutput = builder.technicalOutput;
    this.startDate = builder.startDate;
    this.dueDate = builder.dueDate;
    this.comments = builder.comments;
  }

  public static class WorkPackageBuilder {

    private WorkBreakdownStructure workBreakdownStructure;
    private String name;
    private String version;
    private String status;
    private String code;
    private String networkActivity;
    private String operationalActivity;
    private String purchaseOrder;
    private String technicalOutput;
    private String technicalInput;
    private Date startDate;
    private Date dueDate;
    private String comments;

    //mandatory fields
    public WorkPackageBuilder(final WorkBreakdownStructure workBreakdownStructure, final String name,
          final String version,
          final String status, final String code) {
      this.workBreakdownStructure = workBreakdownStructure;
      this.name = name;
      this.version = version;
      this.status = status;
      this.code = code;
    }

    public WorkPackageBuilder networkActivity(final String networkActivity) {
      this.networkActivity = networkActivity;
      return this;
    }

    public WorkPackageBuilder operationalActivity(final String operationalActivity) {
      this.operationalActivity = operationalActivity;
      return this;
    }

    public WorkPackageBuilder purchaseOrder(final String purchaseOrder) {
      this.purchaseOrder = purchaseOrder;
      return this;
    }

    public WorkPackageBuilder technicalOutput(final String technicalOutput) {
      this.technicalOutput = technicalOutput;
      return this;
    }

    public WorkPackageBuilder technicalInput(final String technicalInput) {
      this.technicalInput = technicalInput;
      return this;
    }

    public WorkPackageBuilder startDate(final Date startDate) {
      this.startDate = startDate;
      return this;
    }

    public WorkPackageBuilder dueDate(final Date dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public WorkPackageBuilder comments(final String comments) {
      this.comments = comments;
      return this;
    }

    public WorkPackage build() {
      return new WorkPackage(this);
    }
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public WorkBreakdownStructure getWorkBreakdownStructure() {
    return workBreakdownStructure;
  }

  public void setWorkBreakdownStructure(final WorkBreakdownStructure workBreakdownStructure) {
    this.workBreakdownStructure = workBreakdownStructure;
  }

  public List<WorkPackageStatusEvent> getStatusEvents() {
    return statusEvents;
  }

  public void setStatusEvents(List<WorkPackageStatusEvent> statusEvents) {
    this.statusEvents = statusEvents;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
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

  public Integer getRequestedCount() {
    return requestedCount;
  }

  public void setRequestedCount(final Integer requestedCount) {
    this.requestedCount = requestedCount;
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

  public String getServiceOrder() {
    return serviceOrder;
  }

  public void setServiceOrder(final String serviceOrder) {
    this.serviceOrder = serviceOrder;
  }

  public String getOperationalActivity() {
    return operationalActivity;
  }

  public void setOperationalActivity(final String operationalActivity) {
    this.operationalActivity = operationalActivity;
  }

  public String getNetworkActivity() {
    return networkActivity;
  }

  public void setNetworkActivity(final String networkActivity) {
    this.networkActivity = networkActivity;
  }
}
