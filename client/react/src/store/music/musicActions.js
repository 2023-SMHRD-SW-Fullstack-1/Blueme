/*
작성자: 이지희
날짜(수정포함): 2023-09-27
설명: 음악관련 리덕스 액션, 접근해있는 컴포넌트 확인(0922), 총재생시간-DURATION 추가(0924), 주석추가(0927)
*/

export const SET_MUSIC_IDS = "SET_MUSIC_IDS";  // 재생할 음악 목록
export const SET_PLAYING_STATUS = "SET_PLAYING_STATUS"; // 재생 상태
export const SET_CURRENT_SONG_ID = "SET_CURRENT_SONG_ID"; // 현재 재생곡
export const SET_SHOW_MINI_PLAYER = "SET_SHOW_MINI_PLAYER"; // 미니플레이어 On/Off
export const SET_CURRENT_TIME = 'SET_CURRENT_TIME'; // 현재 재생시간
export const SET_DRAGGING_STATUS = 'SET_DRAGGING_STATUS'; // 드래그상태(재생바 관리)
export const SET_DURATION = 'SET_DURATION'; // 총재생시간
export const SET_REPEAT_MODE = 'SET_REPEAT_MODE'; // 한곡반복
export const SET_IS_MUSIC_PLAYER = 'SET_IS_MUSIC_PLAYER'; // 뮤직플레이어 컴포넌트 확인(자동재생 확인)  
export const CLEAR_PLAYING = 'CLEAR_PLAYING'; // 현재 재생음악 삭제(로그아웃시)



// 음악 재생할 목록
export function setMusicIds(musicIds) {
  return {
    type: SET_MUSIC_IDS,
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

// 현재 재생시간
export function setCurrentTime(time) {
  return {
    type: SET_CURRENT_TIME,
    payload: time,
  };
}

// 총 재생시간
export function setDuration(duration) {
  return {
    type: SET_DURATION,
    payload: duration,
  };
}

// 드래그 상태
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

// 접근해있는 컴포넌트 (메인플레이어 or 미니플레이어)
export function setIsMusicPlayer(musicPlayer) {
  return {
    type: SET_IS_MUSIC_PLAYER,
    payload: musicPlayer,
  };
}

// 로그아웃 시 현재재생음악 삭제
export function clearPlaying() {
  return {
      type: CLEAR_PLAYING,
  }
}