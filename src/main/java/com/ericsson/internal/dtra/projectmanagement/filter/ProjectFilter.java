package com.ericsson.internal.dtra.projectmanagement.filter;

import java.util.Date;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Bundling;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.GlobalServiceCenter;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Region;

public final class ProjectFilter {

  private String name;
  private GlobalServiceCenter globalServiceCenter;
  private Region region;
  private Country country;
  private ProductArea productArea;
  private Bundling bundling;
  private Integer network;
  private Integer sapProjectId;
  private Date creationDate;
  private Date startDate;
  private Date endDate;
  private String customer;
  private String label;
  private String status;
  private Integer workBreakdownStructureCompletionRate;
  private Integer workPackageCompletionRate;

  public String getName() {
    return name;
  }

  public GlobalServiceCenter getGlobalServiceCenter() {
    return globalServiceCenter;
  }

  public Region getRegion() {
    return region;
  }

  public Country getCountry() {
    return country;
  }

  public ProductArea getProductArea() {
    return productArea;
  }

  public Bundling getBundling() {
    return bundling;
  }

  public Integer getNetwork() {
    return network;
  }

  public Integer getSapProjectId() {
    return sapProjectId;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getCustomer() {
    return customer;
  }

  public String getLabel() {
    return label;
  }

  public String getStatus() {
    return status;
  }

  public Integer getWorkBreakdownStructureCompletionRate() {
    return workBreakdownStructureCompletionRate;
  }

  public Integer getWorkPackageCompletionRate() {
    return workPackageCompletionRate;
  }

  public static class ProjectFilterBuilder {

    private String name;
    private GlobalServiceCenter globalServiceCenter;
    private Region region;
    private Country country;
    private ProductArea productArea;
    private Bundling bundling;
    private Integer network;
    private Integer sapProjectId;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
    private String customer;
    private String label;
    private String status;
    private Integer workBreakdownStructureCompletionRate;
    private Integer workPackageCompletionRate;

    public ProjectFilterBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ProjectFilterBuilder withGlobalServiceCenter(GlobalServiceCenter globalServiceCenter) {
      this.globalServiceCenter = globalServiceCenter;
      return this;
    }

    public ProjectFilterBuilder withRegion(Region region) {
      this.region = region;
      return this;
    }

    public ProjectFilterBuilder withCountry(Country country) {
      this.country = country;
      return this;
    }

    public ProjectFilterBuilder withProductArea(ProductArea productArea) {
      this.productArea = productArea;
      return this;
    }

    public ProjectFilterBuilder withBundling(Bundling bundling) {
      this.bundling = bundling;
      return this;
    }

    public ProjectFilterBuilder withNetwork(Integer network) {
      this.network = network;
      return this;
    }

    public ProjectFilterBuilder withSapProjectId(Integer sapProjectId) {
      this.sapProjectId = sapProjectId;
      return this;
    }

    public ProjectFilterBuilder withCreationDate(Date creationDate) {
      this.creationDate = creationDate;
      return this;
    }

    public ProjectFilterBuilder withStartDate(Date startDate) {
      this.startDate = startDate;
      return this;
    }

    public ProjectFilterBuilder withEndDate(Date endDate) {
      this.endDate = endDate;
      return this;
    }

    public ProjectFilterBuilder withCustomer(String customer) {
      this.customer = customer;
      return this;
    }

    public ProjectFilterBuilder withLabel(String label) {
      this.label = label;
      return this;
    }

    public ProjectFilterBuilder withStatus(String status) {
      this.status = status;
      return this;
    }

    public ProjectFilterBuilder withWorkBreakdownStructureCompletionRate(Integer workBreakdownStructureCompletionRate) {
      this.workBreakdownStructureCompletionRate = workBreakdownStructureCompletionRate;
      return this;
    }

    public ProjectFilterBuilder withWorkPackageCompletionRate(Integer workPackageCompletionRate) {
      this.workPackageCompletionRate = workPackageCompletionRate;
      return this;
    }

    public ProjectFilter build() {
      ProjectFilter filter = new ProjectFilter();
      filter.name = this.name;
      filter.name = this.name;
      filter.globalServiceCenter = this.globalServiceCenter;
      filter.region = this.region;
      filter.country = this.country;
      filter.productArea = this.productArea;
      filter.bundling = this.bundling;
      filter.network = this.network;
      filter.sapProjectId = this.sapProjectId;
      filter.creationDate = this.creationDate;
      filter.startDate = this.startDate;
      filter.endDate = this.endDate;
      filter.customer = this.customer;
      filter.label = this.label;
      filter.status = this.status;
      filter.workBreakdownStructureCompletionRate = this.workBreakdownStructureCompletionRate;
      filter.workPackageCompletionRate = this.workPackageCompletionRate;
      return filter;
    }
  }
}
