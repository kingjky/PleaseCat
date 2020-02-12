package com.ssafy.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

// @Configuration 애노테이션을 붙여 이 class 파일이 구성 파일임을 선언
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String uploadImagesPath;
	
	//@Value : application.properties에 설정한 이미지 업로드 경로에 관란 프로퍼티 값을 가져온다.
	//  D:/FinalGitCat/PleaseCat/back/src/main/resources/static/images/ (\백슬래쉬 주먼 안됨)
	//  그리고 properties에 값 넣어줄때 '' 주면 안됨
	public WebConfig(@Value("${custom.path.upload-images}") String uploadImagesPath) {
		this.uploadImagesPath = uploadImagesPath;
	}
	
	
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// addResourceLocations에 url경로를 설정하고, 
		// addResourceLocations에 매핑될 리소스 위치를 설정한다.(경로앞에 file:///을 반드시 붙여줄것)
		registry.addResourceHandler("satic/swagger-ui.html")
		.addResourceLocations("classpath:/METH-INF/resources/");
		 registry
	        .addResourceHandler("/webjars/**")
	        .addResourceLocations("classpath:/META-INF/resources/webjars/");
		
		/*
		 * 즉, /static/image/email/나, static/image/etc/로 시작되는 url pattern은 
		 * 일반적인 정적 파일 경로가 아닌 우리가 별도로 설정한 파일 업로드 경로인 ~
		 * 폴더내에 있는 리소스를 가리키도록 하는 것
		 */
		
	
		List<String> imageFolders = Arrays.asList("cat","post","user");
		for(String imageFolder : imageFolders) {
			registry.addResourceHandler("/static/images/"+imageFolder+"/**")
			.addResourceLocations("file:///"+uploadImagesPath+imageFolder+"/")
			.setCachePeriod(3600)
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
		}
		
		
	}
	
	
}
