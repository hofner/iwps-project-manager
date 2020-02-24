package com.ericsson.internal.dtra.projectmanagement.permissions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;

import com.querydsl.core.types.dsl.StringPath;

import se.ericsson.internal.csdp.authorization.oauth2.ResourcePermission;
import se.ericsson.internal.csdp.authorization.utils.CSDPPermissionUtils;

public abstract class ResourcePermissionBase {
  /**
   * Map containing a resource permission string key and a database field name value
   */
  protected Map<String, StringPath> typeCollection = new HashMap<>();

  /**
   * If a permission has a value all allow all
   * @param permissions which will be compared with the resource added and see if its value is ALL
   * @return true if one of the received resource permission type contains an ALL value, false otherwise
   */
  protected boolean permissionContainsValueAll(Collection<ResourcePermission> permissions) {
    return permissions.stream().anyMatch(p -> "ALL".equalsIgnoreCase(p.getValue()) && typeCollection.containsKey(p.getType()));
  }

  /**
  * Validate if user has at least one of the received resource permissions
  * @param permissions which are contextual permission from authorization.
  * @throws UserDeniedAuthorizationException if there are no valid permissions for received resource permissions
  */
  protected void validateForEmptyPermissions(Collection<ResourcePermission> permissions) {
    if (!permissions.stream().anyMatch(p -> typeCollection.containsKey(p.getType()))) {
      throw new UserDeniedAuthorizationException("User does not have the resource permissions to do this action");
    }
  }

  public Collection<ResourcePermission> getContextualPermissions() {
    return CSDPPermissionUtils.getContextualResourcePermissions();
  }
}
