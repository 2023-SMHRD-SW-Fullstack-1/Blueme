/*
작성자: 이지희
날짜(수정포함): 2023-09-12
설명: 음악 플레이리스트 리덕스 파일 추가
*/
/*
작성자: 이유영
날짜(수정포함): 2023-09-15
설명: 회원 관리 리덕스 추가
*/

import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";

// 지희 시작
import { Provider } from "react-redux"; // Provider import
import musicStore from "./store/music/musicStore"; 
// 지희 끝

//유영 시작
  // In your store.js file 
  import { createStore } from 'redux';
  import rootReducer from './store/rootReducer';
  
   const store = createStore(
    rootReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    );
//유영 끝

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <React.StrictMode>
  // 지희 시작
  <Provider store={store}>
    <App />
  </Provider>
  // 지희 끝
  // </React.StrictMode>
);

reportWebVitals();
