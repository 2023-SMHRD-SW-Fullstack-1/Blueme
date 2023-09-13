package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class IndexController {

	// 테스트용
	@GetMapping("/index")
	public String test() {
		return "";
	}

}
