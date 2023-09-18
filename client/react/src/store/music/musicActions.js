/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 음악관련 리덕스 액션
*/

export const SET_MUSIC_IDS = 'SET_MUSIC_IDS';
export const SET_PLAYING_STATUS = 'SET_PLAYING_STATUS';
export const SET_CURRENT_SONG_ID = 'SET_CURRENT_SONG_ID';
export const SET_SHOW_MINI_PLAYER = "SET_SHOW_MINI_PLAYER";

export function setMusicIds(musicIds) {
  return {
    type: "SET_MUSIC_IDS",
    payload: musicIds,
  };
}

export function setPlayingStatus(playingStatus) {
   return {
     type: SET_PLAYING_STATUS,
     payload: playingStatus,
   };
 }
 
 export function setCurrentSongId(songId) {
   return {
     type: SET_CURRENT_SONG_ID,
     payload: songId,
   };
 }
 
 export function setShowMiniPlayer(show) {
   return {
     type: SET_SHOW_MINI_PLAYER,
     payload: show,
   };
 }

