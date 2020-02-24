package com.ericsson.internal.dtra.projectmanagement.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class ProjectStatusSummary {

  private String status;
  private Long count;

  public ProjectStatusSummary(final String status, final Long count) {
    this.count = count;
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  /**
   * Row Mapper class to map JDBC Template result set to a ProjectStatusSummary.
   * The Mapper will try mapping the first result as the {@code status} and the
   * second as the {@code count}
   */
  public static class ProjectStatusSummaryRowMapper implements RowMapper<ProjectStatusSummary> {
    @Override
    public ProjectStatusSummary mapRow(ResultSet rowSet, int rownumber) throws SQLException {
      return new ProjectStatusSummary(rowSet.getString(1), rowSet.getLong(2));
    }
  }
}