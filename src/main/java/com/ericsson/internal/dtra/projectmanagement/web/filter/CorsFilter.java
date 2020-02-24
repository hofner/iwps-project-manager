package com.ericsson.internal.dtra.projectmanagement.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
    httpResponse.setHeader("Access-Control-Max-Age", "3600");
    httpResponse.setHeader(
          "Access-Control-Allow-Headers",
          "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, token");

    // handle pre-flight requests
    if (request instanceof RequestFacade) {
      RequestFacade requestFacade = (RequestFacade) request;
      if ("OPTIONS".equalsIgnoreCase(requestFacade.getMethod())) {
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        return;
      }
    }

    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) {
    // nothing to do
  }

  @Override
  public void destroy() {
    // nothing to do
  }

}
