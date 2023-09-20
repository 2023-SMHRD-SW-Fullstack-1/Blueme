/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: 음악관련 리덕스 액션
*/

export const SET_MUSIC_IDS = "SET_MUSIC_IDS";
export const SET_PLAYING_STATUS = "SET_PLAYING_STATUS";
export const SET_CURRENT_SONG_ID = "SET_CURRENT_SONG_ID";
export const SET_SHOW_MINI_PLAYER = "SET_SHOW_MINI_PLAYER";
export const SET_CURRENT_TIME = 'SET_CURRENT_TIME';
export const SET_DURATION = 'SET_DURATION';
export const SET_DRAGGING_STATUS = 'SET_DRAGGING_STATUS';
export const SET_REPEAT_MODE = 'SET_REPEAT_MODE';
export const SEEK_TO = 'SET_REPEAT_MODE';


// 음악 재생할 목록
export function setMusicIds(musicIds) {
  return {
    type: "SET_MUSIC_IDS",
    payload: musicIds,
  };
}

// 음악 재생 상태
export function setPlayingStatus(playingStatus) {
  return {
    type: SET_PLAYING_STATUS,
    payload: playingStatus,
  };
}

// 현재 재생 곡 ID
export function setCurrentSongId(songId) {
  return {
    type: SET_CURRENT_SONG_ID,
    payload: songId,
  };
}

// 미니플레이어 UI
export function setShowMiniPlayer(show) {
  return {
    type: SET_SHOW_MINI_PLAYER,
    payload: show,
  };
}

// 재생시간
export function setCurrentTime(time) {
  return {
    type: SET_CURRENT_TIME,
    payload: time,
  };
}

export function setDuration(duration) {
  return {
    type: SET_DURATION,
    payload: duration,
  };
}

export function setDraggingStatus(draggingStatus) {
  return {
    type: SET_DRAGGING_STATUS,
    payload: draggingStatus,
  };
}

// 한곡반복
export function setRepeatMode(repeat) {
  return {
    type: SET_REPEAT_MODE,
    payload: repeat,
  };
}
