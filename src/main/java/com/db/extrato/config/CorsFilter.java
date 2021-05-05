package com.db.extrato.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

@Log
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("Iniciando filtro CORS...");
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    
    log.info("Autorizando requisição CORS...");
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Credentials", "true");
    
    if((req.getMethod().equals("OPTIONS")) && "http://localhost:4200".equals(req.getHeader("Origin"))) {
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, Expires, Pragma");
        res.setHeader("Access-Control-Allow-Max-Age", "3600");
        res.setStatus(HttpServletResponse.SC_OK);
    }else {
        chain.doFilter(request, response);
    }
    
  }

}
