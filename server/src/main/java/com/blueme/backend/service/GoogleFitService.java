package com.blueme.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleFitService {
	 
	private OAuth2AuthorizedClientService authorizedClientService;
	
	 public String getFitnessData(OAuth2AuthenticationToken authentication) {
	        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
	            authentication.getAuthorizedClientRegistrationId(),
	            authentication.getName());

	        long startTimeNanos = Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli() * 1_000_000;
	        long endTimeNanos = Instant.now().toEpochMilli() * 1_000_000;
        
	        //String userInfoEndpointUri = "https://www.googleapis.com/fitness/v1/users/me/dataset/{dataSetId}";
//	        String userInfoEndpointUri = "https://www.googleapis.com/fitness/v1/users/me/dataset/derived:com.google.step_count.delta:com.google.android.gms:estimated_steps/{start-time-in-nanoseconds}-{end-time-in-nanoseconds}";

//	        userInfoEndpointUri = userInfoEndpointUri.replace("{start-time-in-nanoseconds}", String.valueOf(startTimeNanos))
//	        	    .replace("{end-time-in-nanoseconds}", String.valueOf(endTimeNanos));
	        
	        String userInfoEndpointUri = "https://www.googleapis.com/fitness/v1/users/me/dataSources";
	        
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
	        
	        HttpEntity entity = new HttpEntity("", headers);
	        
	        ResponseEntity<String> responseEntity =
	            restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);
	        
	        return responseEntity.getBody();
	    }
}
