package com.crbt.api.services.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

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

@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
public class SimpleCorsFilter implements Filter {

	public SimpleCorsFilter() {
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
			throws IOException, ServletException {
		//String[] allowDomain = {"http://localhost:4200","http://localhost:8181","http://localhost:8080"};
		
		HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;		        
        response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Origin", "http://182.74.113.60:8090");
        //response.setHeader("Access-Control-Allow-Origin", "http://10.111.222.112:8080");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, authorization, content-type");
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        	response.setStatus(HttpServletResponse.SC_OK);
        } else {
        	chain.doFilter(req, res);
        }        
        
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
}
