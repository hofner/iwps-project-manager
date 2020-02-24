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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectDetailView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "project")
public class Project extends StatefulAbstractAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(ProjectView.class)
  private Integer id;

  @JsonView(ProjectView.class)
  private String externalReferenceId;

  @Column(nullable = false)
  @JsonView(ProjectView.class)
  private String name;

  @Column(nullable = false)
  @JsonView(ProjectView.class)
  private String timezone;

  @ManyToOne
  @JoinColumn
  @JsonView(ProjectView.class)
  private GlobalServiceCenter globalServiceCenter;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonView(ProjectDetailView.class)
  private CostModel costModel;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonView(ProjectView.class)
  private Region region;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonView(ProjectView.class)
  private Country country;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonView(ProjectView.class)
  private ProductArea productArea;

  @ManyToOne
  @JsonView(ProjectDetailView.class)
  private Bundling bundling;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  @JsonView(ProjectDetailView.class)
  private List<WorkBreakdownStructure> workBreakdownStructures;

  @JsonView(ProjectView.class)
  private Long standardNetwork;

  @JsonView(ProjectView.class)
  private String sapProjectId;

  @JsonView(ProjectView.class)
  private Date startDate;

  @JsonView(ProjectView.class)
  private Date dueDate;

  @Column(nullable = false)
  @JsonView(ProjectView.class)
  private String customer;

  @JsonView(ProjectView.class)
  private Integer workBreakdownStructureCompletionRate;

  @JsonView(ProjectView.class)
  private Integer workPackageCompletionRate;

  @JsonView(ProjectView.class)
  private String comment;

  @JsonView(ProjectView.class)
  private String customerProjectManagerFullname;

  @JsonView(ProjectView.class)
  private String customerProjectManagerSignum;

  @JsonView(ProjectView.class)
  private String serviceProjectManagerFullname;

  @JsonView(ProjectView.class)
  private String serviceProjectManagerSignum;

  @JsonView(ProjectDetailView.class)
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "assisting_customer_project_manager_project", joinColumns = {
        @JoinColumn(name = "project_id", nullable = false) }, inverseJoinColumns = {
              @JoinColumn(name = "resource_id", nullable = false) }, uniqueConstraints = {
                    @UniqueConstraint(columnNames = { "project_id", "resource_id" }) })
  private List<AssistingCustomerProjectManager> assistingCustomerProjectManagers;

  @JsonView(ProjectDetailView.class)
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "assisting_service_project_manager_project", joinColumns = {
        @JoinColumn(name = "project_id", nullable = false) }, inverseJoinColumns = {
              @JoinColumn(name = "resource_id", nullable = false) }, uniqueConstraints = {
                    @UniqueConstraint(columnNames = { "project_id", "resource_id" }) })
  private List<AssistingServiceProjectManager> assistingServiceProjectManagers;

  @JsonView(ProjectView.class)
  private String sapProjectDescription;

  private Project() {
  }

  public Project(
        final String name,
        final String timezone,
        final Region region,
        final Country country,
        final ProductArea productArea,
        final String customer) {
    super();
    this.name = name;
    this.timezone = timezone;
    this.region = region;
    this.country = country;
    this.productArea = productArea;
    this.customer = customer;
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getExternalReferenceId() {
    return externalReferenceId;
  }

  public void setExternalReferenceId(final String externalReferenceId) {
    this.externalReferenceId = externalReferenceId;
  }

  public GlobalServiceCenter getGlobalServiceCenter() {
    return globalServiceCenter;
  }

  public void setGlobalServiceCenter(final GlobalServiceCenter globalServiceCenter) {
    this.globalServiceCenter = globalServiceCenter;
  }

  public Bundling getBundling() {
    return bundling;
  }

  public void setBundling(final Bundling bundling) {
    this.bundling = bundling;
  }

  public Long getStandardNetwork() {
    return standardNetwork;
  }

  public void setStandardNetwork(final Long standardNetwork) {
    this.standardNetwork = standardNetwork;
  }

  public String getSapProjectId() {
    return sapProjectId;
  }

  public void setSapProjectId(final String sapProjectId) {
    this.sapProjectId = sapProjectId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(final Date startDate) {
    this.startDate = startDate;
  }

  public Integer getWorkBreakdownStructureCompletionRate() {
    return workBreakdownStructureCompletionRate;
  }

  public void setWorkBreakdownStructureCompletionRate(final Integer workBreakdownStructureCompletionRate) {
    this.workBreakdownStructureCompletionRate = workBreakdownStructureCompletionRate;
  }

  public Integer getWorkPackageCompletionRate() {
    return workPackageCompletionRate;
  }

  public void setWorkPackageCompletionRate(final Integer workPackageCompletionRate) {
    this.workPackageCompletionRate = workPackageCompletionRate;
  }

  public List<WorkBreakdownStructure> getWorkBreakdownStructures() {
    return workBreakdownStructures;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(final Date dueDate) {
    this.dueDate = dueDate;
  }

  public void setWorkBreakdownStructures(final List<WorkBreakdownStructure> workBreakdownStructures) {
    this.workBreakdownStructures = workBreakdownStructures;
  }

  public String getName() {
    return name;
  }

  public String getTimezone() {
    return timezone;
  }

  public Region getRegion() {
    return region;
  }

  public Country getCountry() {
    return country;
  }

  public String getCustomer() {
    return customer;
  }

  public ProductArea getProductArea() {
    return productArea;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(final String comment) {
    this.comment = comment;
  }

  public List<AssistingCustomerProjectManager> getAssistingCustomerProjectManagers() {
    return assistingCustomerProjectManagers;
  }

  public void setAssistingCustomerProjectManagers(
        final List<AssistingCustomerProjectManager> assistingCustomerProjectManagers) {
    this.assistingCustomerProjectManagers = assistingCustomerProjectManagers;
  }

  public List<AssistingServiceProjectManager> getAssistingServiceProjectManagers() {
    return assistingServiceProjectManagers;
  }

  public void setAssistingServiceProjectManagers(
        final List<AssistingServiceProjectManager> assistingServiceProjectManagers) {
    this.assistingServiceProjectManagers = assistingServiceProjectManagers;
  }

  public String getServiceProjectManagerFullname() {
    return serviceProjectManagerFullname;
  }

  public void setServiceProjectManagerFullname(final String serviceProjectManagerFullname) {
    this.serviceProjectManagerFullname = serviceProjectManagerFullname;
  }

  public String getServiceProjectManagerSignum() {
    return serviceProjectManagerSignum;
  }

  public void setServiceProjectManagerSignum(final String serviceProjectManagerSignum) {
    this.serviceProjectManagerSignum = serviceProjectManagerSignum;
  }

  public String getCustomerProjectManagerFullname() {
    return customerProjectManagerFullname;
  }

  public void setCustomerProjectManagerFullname(final String customerProjectManagerFullname) {
    this.customerProjectManagerFullname = customerProjectManagerFullname;
  }

  public String getCustomerProjectManagerSignum() {
    return customerProjectManagerSignum;
  }

  public void setCustomerProjectManagerSignum(final String customerProjectManagerSignum) {
    this.customerProjectManagerSignum = customerProjectManagerSignum;
  }

  public String getSapProjectDescription() {
    return sapProjectDescription;
  }

  public void setSapProjectDescription(final String sapProjectDescription) {
    this.sapProjectDescription = sapProjectDescription;
  }

  public CostModel getCostModel() {
    return costModel;
  }

  public void setCostModel(final CostModel costModel) {
    this.costModel = costModel;
  }
}
