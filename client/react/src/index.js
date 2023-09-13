/*
작성자: 이지희
날짜(수정포함): 2023-09-12
설명: 음악 플레이리스트 리덕스 파일 추가
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

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <React.StrictMode>
  // 지희 시작
  <Provider store={musicStore}>
    <App />
  </Provider>
  // 지희 끝
  // </React.StrictMode>
);

reportWebVitals();
