package com.blueme.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class BackendApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	// SpringSecurity적용시 Auditor 적용
//	@Bean
//	public AuditorAware<String> auditorProvider() {
//	    return () -> {
//	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	        if (authentication == null || !authentication.isAuthenticated()) {
//	            return null;
//	        }
//	        return Optional.ofNullable(authentication.getName());
//	    };
//	}
	
	// 일반 세션에서 데이터 가져올경우
//	 @Bean
//	   public AuditorAware<Long> auditorProvider(SessionUserRetriever retriever) {
//	        return () -> Optional.ofNullable(retriever.getId());
//	 }

}
