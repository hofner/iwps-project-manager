package com.ericsson.internal.dtra.projectmanagement.permissions;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.util.Assert;

import com.ericsson.internal.dtra.projectmanagement.authorization.ResourcePermissionType;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.QProject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;

import se.ericsson.internal.csdp.authorization.oauth2.ResourcePermission;

/**
 * Class which helps search query using resource permissions
 *
 * @author eahmeiq
 *
 */
public class ResourcePermissionQueryGenerator extends ResourcePermissionBase {

  @PersistenceContext
  private EntityManager em;

  private BooleanBuilder permissionCriteria = new BooleanBuilder();
  private JPAQuery<?> searchQuery;

  public ResourcePermissionQueryGenerator(JPAQuery<?> searchQuery) {
    this.searchQuery = searchQuery;
    final QProject project = QProject.project;
    useResource(ResourcePermissionType.REGION.toString(), project.region.name);
    useResource(ResourcePermissionType.GSC.toString(), project.globalServiceCenter.name);
  }

  /**
   * Adds resource type to be extracted from the user's permission list
   * @param type which will be compared with resource permission types. Can not be null.
   * @param databaseFieldName which will be used to search in the database. Can not be null.
   * @throws IllegalArgumentException - if type or databaseFieldName is a null element
   */
  public void useResource(String type, StringPath databaseFieldName) {
    Assert.noNullElements(new Object[] { type, databaseFieldName });
    typeCollection.put(type, databaseFieldName);
  }

  /**
   * Generates a searchQuery using resource permissions.
   * @param projectFilter is your initial criteria you would like to query.
   * @return a generated query containing initial criteria and permission criteria
   * @throws UserDeniedAuthorizationException if user does not have at least one of the received resource
   */
  public JPAQuery generateSearchQueryWithPermissions(Predicate projectFilter) {
    Collection<ResourcePermission> permissions = getContextualPermissions();

    //If user does not have at least one of the received resource, throw exception
    validateForEmptyPermissions(permissions);

    //If a permission has value all, allow all
    if (permissionContainsValueAll(permissions)) {
      searchQuery.where(projectFilter);
      return searchQuery;
    }
    //add filterCriteria and permissionCriteria to searchQuery
    applyResourcePermissionToSearchQuery(projectFilter, permissions);

    return searchQuery;
  }

  private void applyResourcePermissionToSearchQuery(Predicate projectFilter,
        Collection<ResourcePermission> permissions) {
    //extract permissions to permissionCriteria
    for (Entry<String, StringPath> entry : typeCollection.entrySet()) {
      addResourcePermissionsToCriteriaList(permissions, entry);
    }

    //if projectFilter is not empty, use and operator with projectFilter and permissionCriteria
    //else use permissionCriteria
    if (!((BooleanBuilder) projectFilter).hasValue()) {
      BooleanBuilder mainCriteria = new BooleanBuilder();
      searchQuery.where(mainCriteria.andAnyOf(projectFilter, permissionCriteria));
    } else {
      searchQuery.where(permissionCriteria);
    }
  }

  private void addResourcePermissionsToCriteriaList(Collection<ResourcePermission> permissionCollection,
        Entry<String, StringPath> resourceEntry) {

    //filter and extract permission values to be added to criteriaList
    Set<String> permissionStrings = permissionCollection.stream()
          .filter(p -> p.getType().equalsIgnoreCase(resourceEntry.getKey()))
          .map(p -> p.getValue())
          .collect(Collectors.toSet());
    if (!permissionStrings.isEmpty()) {
      permissionCriteria.or(resourceEntry.getValue().in(permissionStrings));
    }
  }
}
