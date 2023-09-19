/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 음악관련 리덕스 스토어
*/

import { createStore } from "redux";
import musicReducer from "./musicReducer"; 
import { setMusicIds, setPlayingStatus, setCurrentSongId, setShowMiniPlayer, setCurrentTime, setDraggingStatus, setRepeatMode } from './musicActions'; // 수정된 부분

const store = createStore(musicReducer);

store.dispatch(setMusicIds([]));
store.dispatch(setPlayingStatus(false));
store.dispatch(setCurrentSongId());
store.dispatch(setShowMiniPlayer(false));
// 지희 추가 (0919)
store.dispatch(setCurrentTime(0));
store.dispatch(setDraggingStatus(false))
store.dispatch(setRepeatMode(false))


export default store;