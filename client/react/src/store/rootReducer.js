/*
작성자: 이유영
날짜(수정포함): 2023-09-16
설명: combineReducer, reducer-persist
*/

/*
작성자: 이지희
날짜(수정포함): 2023-09-24
설명: musicReducer 추가, isMusicPlayer 추가, playingStatus redux-persist 제외
*/
import memberReducer from './member/memberReducer'
import musicReducer from './music/musicReducer';
import { setMusicIds, setPlayingStatus, setCurrentSongId, setShowMiniPlayer, setCurrentTime, setDuration, setDraggingStatus, setRepeatMode, setIsMusicPlayer, clearPlaying } from './music/musicActions';

import { combineReducers, createStore } from 'redux'
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; //localStorage 사용

// 지희 시작
import { createTransform } from 'redux-persist';

const SaveSubsetFilter = createTransform(
    (inboundState, key) => {
      return { ...inboundState, playingStatus: false };
    },
    
    (outboundState, key) => {
      return outboundState;
    },
    
    { whitelist: ['musicReducer'] }
  );

 // 지희 끝

//combineReducer
const rootReducer = combineReducers({
    memberReducer,
    musicReducer,
})

const persistConfig = {
    key: 'root',
    storage,
    // 지희 추가(0924)
    transforms: [SaveSubsetFilter]
};

//새로고침 시 store 정보 사라지는 거 방지(localStorage에 저장해둠)
const persistedReducer = persistReducer(persistConfig, rootReducer);

//store생성/ redux브라우저에서 확인 가능
let store = createStore(
    persistedReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

let persistor = persistStore(store);

store.dispatch(setMusicIds([]));
store.dispatch(setPlayingStatus(false));
store.dispatch(setCurrentSongId(1));
store.dispatch(setShowMiniPlayer(false));

// 지희 추가 (0922)
store.dispatch(setCurrentTime(0));
store.dispatch(setDraggingStatus(false))
store.dispatch(setRepeatMode(false))
store.dispatch(setIsMusicPlayer(false))
store.dispatch(setDuration(0))
store.dispatch(clearPlaying(0))


export { store, persistor };
