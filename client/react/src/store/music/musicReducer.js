/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: 음악관련 리덕스 리듀서 (재셍바 관련 추가)
*/

import { SET_MUSIC_IDS, SET_PLAYING_STATUS, SET_CURRENT_SONG_ID, SET_SHOW_MINI_PLAYER, SET_CURRENT_TIME, SET_DRAGGING_STATUS, SET_DURATION } from './musicActions';

const initialState = {
  musicIds: [],
  playingStatus: false,
  currentSongId: 1,
  showMiniPlayer: false, 
  currentTime: 0,
  duration: 0,
  isDragging: false
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
      return { ...state, currentTime: action.time };
    case SET_DURATION:
      return { ...state, duration: action.duration };
    case SET_DRAGGING_STATUS:
      return { ...state, isDragging: action.isDragging };
    default:
      return state;
  }
}

export default music;