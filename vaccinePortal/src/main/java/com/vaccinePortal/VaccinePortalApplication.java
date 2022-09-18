package com.vaccinePortal;



import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vaccinePortal.entities.Vaccine;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
public class VaccinePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccinePortalApplication.class, args);
		
	}
	@Bean
	public ModelMapper mapper()
	{
		 ModelMapper modelMapper = new ModelMapper();
		 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		 return modelMapper;
	}
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.vaccinePortal"))
                .build()
                .apiInfo(apiInfo());
    }
	 private ApiInfo apiInfo() {
	        return new ApiInfo(
	                "My REST API", //title
	                "Some custom description of API.", //description
	                "Version 1.0", //version
	                "Terms of service", //terms of service URL
	                new Contact("Bhanuka Dissanayake", "www.example.com", "myeaddress@company.com"),
	                "License of API", "API license URL", Collections.emptyList()); // contact info
	    }
}
