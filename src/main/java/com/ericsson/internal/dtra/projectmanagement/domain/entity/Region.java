package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.RegionView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "region")
public class Region extends AbstractAuditableEntity {

  @Id
  @JsonView(RegionView.class)
  private Integer id;

  @Column(nullable = false, unique = true)
  @JsonView({ ProjectView.class, RegionView.class })
  private String name;

  @OneToMany(mappedBy = "region")
  private List<Country> countries;

  public Region() {
    // Nothing to be done
  }

  public Region(final String name) {
    super();
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
