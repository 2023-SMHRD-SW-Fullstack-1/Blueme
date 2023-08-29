package com.blueme.backend.web;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.service.GoogleFitService;

@RestController
public class GoogleFitController {

	private GoogleFitService googleFitService;
	
	@GetMapping("/fitnessData")
    public String getFitnessData(OAuth2AuthenticationToken authentication) {
        return googleFitService.getFitnessData(authentication);
    }
	
	
}
