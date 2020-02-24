package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.CountryView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "country")
public class Country extends AbstractAuditableEntity {

  @Id
  @JsonView(CountryView.class)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "region_id", nullable = false)
  private Region region;

  @Column(nullable = false, unique = true)
  @JsonView({ ProjectView.class, CountryView.class })
  private String name;

  @JsonView(CountryView.class)
  private String timezone;

  //TODO: This could have a better name. Verify the implication with consumers of this field
  @JsonView({ ProjectView.class, CountryView.class })
  private Boolean standardCostModelReady;

  public Country() {
    // Nothing to be done
  }

  public Country(final String name, final String timezone, final Boolean standardCostModelReady) {
    super();
    this.name = name;
    this.timezone = timezone;
    this.standardCostModelReady = standardCostModelReady;
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

  public Region getRegion() {
    return region;
  }

  public void setRegion(final Region region) {
    this.region = region;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(final String timezone) {
    this.timezone = timezone;
  }

  public Boolean getStandardCostModelReady() {
    return standardCostModelReady;
  }

  public void setStandardCostModelReady(final Boolean standardCostModelReady) {
    this.standardCostModelReady = standardCostModelReady;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
