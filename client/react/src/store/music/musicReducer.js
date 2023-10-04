/*
작성자: 이지희
날짜(수정포함): 2023-09-24
설명: 음악관련 리덕스 리듀서 (재셍바 관련 추가), 접근해있는 컴포넌트 확인(0922), 총재생시간-duration추가(0924)
*/

import {
  SET_MUSIC_IDS,
  SET_PLAYING_STATUS,
  SET_CURRENT_SONG_ID,
  SET_SHOW_MINI_PLAYER,
  SET_CURRENT_TIME,
  SET_DRAGGING_STATUS,
  SET_REPEAT_MODE,
  SET_IS_MUSIC_PLAYER,
  SET_DURATION,
  CLEAR_PLAYING
} from "./musicActions";



const initialState = {
  musicIds: [],
  playingStatus: false,
  currentSongId: 1,
  showMiniPlayer: false,
  currentTime: 0,
  isDragging: false,
  repeatMode: false,
  isMusicPlayer : false,
  duration : 0
};

function music(state = initialState, action) {
  switch (action.type) {
    case SET_MUSIC_IDS:
      return { ...state, musicIds: action.payload };
    case SET_PLAYING_STATUS:
      return { ...state, playingStatus: action.payload };
    case SET_CURRENT_SONG_ID:
      return { ...state, currentSongId: action.payload };
    case SET_SHOW_MINI_PLAYER:
      return { ...state, showMiniPlayer: action.payload };
    case SET_CURRENT_TIME:
      return { ...state, currentTime: action.payload };
    case SET_DRAGGING_STATUS:
      return { ...state, draggingStatus: action.payload };
    case SET_REPEAT_MODE:
      return { ...state, repeatMode: action.payload };
    case  SET_IS_MUSIC_PLAYER:
      return { ...state, isMusicPlayer: action.payload };
    case  SET_DURATION:
      return { ...state, duration: action.payload };
    case CLEAR_PLAYING:
       return initialState
    default:
      return state;
  }
}

export default music;
