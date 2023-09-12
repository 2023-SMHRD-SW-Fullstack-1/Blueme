package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.service.RecMusiclistsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recMusiclist")
@CrossOrigin("*")
public class RecMusiclistsController {

  private final RecMusiclistsService recMusiclistsService;

  @PostMapping("/chatGPT/{userId}")
  public Long registerRecMusiclist(@PathVariable("userId") String userId){
    log.info("start register RecMusiclist for userId = {}", userId);
    return recMusiclistsService.registerRecMusiclist(userId);
  }
  
  
}
