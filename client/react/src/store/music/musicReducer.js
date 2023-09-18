/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 음악관련 리덕스 리듀서
*/

import { SET_MUSIC_IDS, SET_PLAYING_STATUS, SET_CURRENT_SONG_ID, SET_SHOW_MINI_PLAYER } from './musicActions';

const initialState = {
  musicIds: [],
  playingStatus: false,
  currentSongId: 1,
  showMiniPlayer: false, 
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
    default:
      return state;
  }
}

export default music;