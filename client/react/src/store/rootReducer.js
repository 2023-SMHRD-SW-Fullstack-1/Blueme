/*
작성자: 이유영
날짜(수정포함): 2023-09-16
설명: combineReducer, reducer-persist
*/
import memberReducer from './member/memberReducer'
import { combineReducers, createStore } from 'redux'
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; //localStorage 사용
import musicReducer from './music/musicReducer'

//combineReducer
const rootReducer = combineReducers({
    memberReducer,
    musicReducer
})

const persistConfig = {
    key: 'root',
    storage,
  };

//새로고침 시 store 정보 사라지는 거 방지(localStorage에 저장해둠)
const persistedReducer = persistReducer(persistConfig, rootReducer);

//store생성/ redux브라우저에서 확인 가능
let store = createStore(
    persistedReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

let persistor = persistStore(store);
  
export { store, persistor };