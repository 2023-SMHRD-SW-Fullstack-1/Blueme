/*
작성자: 이지희
날짜(수정포함): 2023-09-16
설명: 전역적 음악 재생
*/

import { useEffect, useState, useRef } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { Howl } from "howler";

import {
   setMusicIds,
   setCurrentSongId,
   setPlayingStatus
 } from "../../store/music/musicActions";
 

const SoundControl = () => {

   const navigate = useNavigate();
   const location = useLocation();
   const dispatch = useDispatch();
 
 
   // useState
   const [duration, setDuration] = useState(0);
   const [isPlaying, setIsPlaying] = useState(false);
   const [sound, setSound] = useState(null);
   // 재생바 이동 관련 useState
   const [isDragging, setIsDragging] = useState(false);
   // 한곡반복
   const [isRepeatMode, setIsRepeatMode] = useState(false);
   const isRepeatModeRef = useRef(isRepeatMode); // Ref 생성
   const [currentTime, setCurrentTime] = useState(0);

   // 음악 재생 인덱스 (리덕스 활용)
   const musicIds = useSelector((state) => state.musicReducer.musicIds);
   const [currentSongIndex, setCurrentSongIndex] = useState(-1);
   const currentSongId = useSelector((state) => state.musicReducer.currentSongId);
   const playingStatus = useSelector((state) => state.musicReducer.playingStatus);
   

   useEffect(() => {
      const fetchSound = async () => {

        try {
          // 이전 사운드 언로드
          if (sound) sound.unload();
  
          // 새로운 사운드 로드 및 재생
          const newSound = new Howl({
            src: [`/music/${currentSongId}`],
            html5: true,
            format: ["mpeg"],
            onload() {
              setDuration(newSound.duration());
              setCurrentTime(0);
              dispatch(setPlayingStatus(true));
              newSound.play();
            },
            onend() {
              if (isRepeatModeRef.current) {
                newSound.seek(0);
                setCurrentTime(0);
                newSound.play();
              } else {
               // nextTrack 로직
               let nextIndex =  musicIds.indexOf(Number(currentSongId)) + 1;           
                  if (nextIndex >= musicIds.length) {
                  nextIndex = 0;
                  }
               const nextSongId = musicIds[nextIndex];
               dispatch(setCurrentSongId(nextSongId));
              }
            },
            onplay() {
              setIsPlaying(true);
            },
            onpause() {
              setIsPlaying(false);
            },
          });
  
          setSound(newSound);

        } catch (error) {
          console.error("음악 불러오기 실패", error);
        }
      };
  
      fetchSound();
  
      return () => {
        if (sound) sound.unload();
      };
    }, [currentSongId, musicIds]);
  
    // 한곡반복 Ref
    useEffect(() => {
      isRepeatModeRef.current = isRepeatMode;
    }, [isRepeatMode]);
  
    useEffect(() => {
      if (sound != null) { 
          setCurrentTime(sound.seek());
      }
  
      if (!playingStatus){
          if (sound != null && sound.playing()) {
              sound.pause();
          }
      } else {
          if (sound != null) {
              if (!sound.playing()) {
                  sound.play();
              }
           }
       }
  }, [playingStatus]);


   // 리턴문
  return null;
}

export default SoundControl