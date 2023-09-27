/*
작성자: 이지희
날짜(수정포함): 2023-09-22
설명: 전역적 음악 재생 관리, 접근해있는 컴포넌트 확인(0922)
*/

import { useEffect, useState, useRef } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Howl } from "howler";

import {
  setCurrentSongId,
  setPlayingStatus,
  setCurrentTime,
  setDuration,
} from "../../store/music/musicActions";
import MusicPlayer from "../../pages/MusicPlayer";

const SoundControl = () => {
  const dispatch = useDispatch();

  // useState
  const [sound, setSound] = useState(null);

  // 음악 재생 (리덕스 활용)
  const musicIds = useSelector((state) => state.musicReducer.musicIds);
  const currentSongId = useSelector(
    (state) => state.musicReducer.currentSongId
  );
  const playingStatus = useSelector(
    (state) => state.musicReducer.playingStatus
  );
  const draggingStatus = useSelector(
    (state) => state.musicReducer.draggingStatus
  );
  const currentTime = useSelector((state) => state.musicReducer.currentTime);
  // 한곡반복
  const repeatMode = useSelector((state) => state.musicReducer.repeatMode);
  const repeatModeRef = useRef(repeatMode);
  // 접근해있는 컴포넌트 확인 (뮤직플레이어 or 미니플레이어)
  const isMusicPlayer = useSelector((state) => state.musicReducer.isMusicPlayer);

  // 음원 불러오기
  useEffect(() => {
    const fetchSound = async () => {
      try {
        // 이전 사운드 언로드
        if (sound) sound.unload();

        // 새로운 사운드 로드 및 재생
        const newSound = new Howl({
          src: [`${process.env.REACT_APP_API_BASE_URL}/music/${currentSongId}`],
          format: ["mpeg"],
          onload() {
            dispatch(setCurrentTime(0));
            if (isMusicPlayer) {
              newSound.play();
              dispatch(setPlayingStatus(!playingStatus));
            } 
            else {
              if (playingStatus){
                newSound.play();
                dispatch(setPlayingStatus(!playingStatus));
              }
             }
            dispatch(setDuration(newSound.duration()))            
            },
          onend() {
            if (repeatModeRef.current) {
              newSound.seek(0);
              dispatch(setCurrentTime(0));
              newSound.play();
            } else {
              // nextTrack 로직
              let nextIndex = musicIds.indexOf(Number(currentSongId)) + 1;
              if (nextIndex >= musicIds.length) {
                nextIndex = 0;
              }
              let nextSongId = musicIds[nextIndex];
              dispatch(setCurrentSongId(nextSongId));
            }
          },
          onplay() {
            dispatch(setPlayingStatus(true));
          },
          onpause() {
            dispatch(setPlayingStatus(false));
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



  
  // 한곡반복
  useEffect(() => {
    repeatModeRef.current = repeatMode;
  }, [repeatMode]);

  // 재생&정지
  useEffect(() => {
    if (sound != null) {
      setCurrentTime(sound.seek());
    }
    if (!playingStatus) {
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

  // 재생시간 저장
  useEffect(() => {
  if (sound && sound.playing()) {
    let intervalId = setInterval(() => {
      dispatch(setCurrentTime(sound.seek()));
    }, 1000);

    // 컴포넌트가 언마운트되거나 음악이 일시 정지될 때 타이머를 정리
    if (sound && sound.playing() == false) {
      clearInterval(intervalId);
    }
    return () => clearInterval(intervalId);
  }
}, [sound, playingStatus, currentSongId]);

  // 드래그 - 재생 위치 변경
  useEffect(() => {
    if (sound != null && !draggingStatus) {
      sound.seek(currentTime);
    }
  }, [currentTime, draggingStatus]);

  // 리턴문
  return null;
};

export default SoundControl;
