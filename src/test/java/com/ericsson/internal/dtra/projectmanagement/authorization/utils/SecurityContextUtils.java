package com.ericsson.internal.dtra.projectmanagement.authorization.utils;

import java.util.Arrays;
import java.util.Collections;

import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.ericsson.internal.dtra.projectmanagement.dataprovider.TestUserEnum;

import se.ericsson.internal.csdp.authorization.oauth2.CSDPUsernamePasswordAuthenticationToken;
import se.ericsson.internal.csdp.authorization.oauth2.RolePermissions;

public class SecurityContextUtils {

  /**
   * This function adds a test user to the {@link SecurityContext} by providing {@link OAuth2Authentication} for the test user specified
   * @param testUser : Test user to add to the context
   */
  public static void addUserToContext(TestUserEnum testUser) {

    CSDPUsernamePasswordAuthenticationToken token = new CSDPUsernamePasswordAuthenticationToken(testUser.getUsername(),
          Collections.emptyList(), Arrays.asList(new RolePermissions(testUser.getPermissions())));
    OAuth2Authentication auth = new OAuth2Authentication(null, token);
    // Prepare security context
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);

  }

  /**
   * This function removes the dummy User added to {@link SecurityContext} by nullifying {@link OAuth2Authentication}
   */
  public static void removeUserFromContext() {
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(null);
    SecurityContextHolder.setContext(securityContext);
  }
}

