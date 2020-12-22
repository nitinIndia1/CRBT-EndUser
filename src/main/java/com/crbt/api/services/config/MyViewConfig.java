package com.crbt.api.services.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

@Configuration
public class MyViewConfig extends WebMvcConfigurerAdapter{

	@Override
	public void configureMessageConverters( List<HttpMessageConverter<?>> converters ){
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper(){
			final long serialVersionUID = 1L;
			@Override
			protected DefaultSerializerProvider _serializerProvider( SerializationConfig config ){
				return super._serializerProvider( config.withView(Views.PageData.class) );
			}
		};
		mapper.configure( MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		converter.setObjectMapper(mapper);
		converters.add(converter);
	}
}
