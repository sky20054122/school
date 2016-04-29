package com.brxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApplication {
	
	/*@Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory= new  MultipartConfigFactory();
        factory.setLocation("E:/upload");
        factory.setMaxFileSize("120MB");  
        factory.setMaxRequestSize("120MB");  
        return factory.createMultipartConfig();  
    }  */

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}
}
