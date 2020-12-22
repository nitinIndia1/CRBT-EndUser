package com.crbt.api.services.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.InitParameterConfiguringServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

import com.captcha.botdetect.web.servlet.CaptchaServlet;



@Configuration
@EnableAutoConfiguration( exclude = ErrorMvcAutoConfiguration.class )
@EntityScan( basePackages = {"com.crbt.api.services.domain"} )
@ComponentScan( basePackages = {"com.crbt.api.services.*"})
@EnableJpaRepositories( basePackages = {"com.crbt.api.services.repository"})
@SpringBootApplication
public class CrbtEndUserApiApplication{

	//@Value("${http.port}")
	//private int httpPort;
	
	public static void main(String[] args)  {
	//	SpringApplication.run(CrbtEndUserApiApplication.class, args);
		ApplicationContext context = SpringApplication.run(CrbtEndUserApiApplication.class, args);
		DispatcherServlet dispatcherServlet = (DispatcherServlet) context.getBean("dispatcherServlet");
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}
	
	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new CaptchaServlet());
	    registration.setUrlMappings(Arrays.asList("/botdetectcaptcha"));
	    return registration;
	}
	
	@Bean
	public ServletContextInitializer initializer() {
	  return new ServletContextInitializer() {

	    @Override
	    public void onStartup(ServletContext servletContext) throws ServletException {
	      servletContext.setInitParameter("BDC_configFileLocation", "/resources/botdetect.xml");
	    }
	  };
	}
	
	@Bean
	public InitParameterConfiguringServletContextInitializer initParamsInitializer() {
	   Map<String, String> contextParams = new HashMap<>();
	   contextParams.put("BDC_configFileLocation", "/resources/botdetect.xml");
	   return new InitParameterConfiguringServletContextInitializer(contextParams);
	}
	
	

	/*
	 * @Bean public EmbeddedServletContainerCustomizer customizeTomcatConnector() {
	 * 
	 * return new EmbeddedServletContainerCustomizer() {
	 * 
	 * @Override public void customize(ConfigurableEmbeddedServletContainer
	 * container) {
	 * 
	 * if (container instanceof TomcatEmbeddedServletContainerFactory) {
	 * TomcatEmbeddedServletContainerFactory containerFactory =
	 * (TomcatEmbeddedServletContainerFactory) container; Connector connector = new
	 * Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
	 * connector.setPort(httpPort);
	 * containerFactory.addAdditionalTomcatConnectors(connector); } } }; }
	 */
}
