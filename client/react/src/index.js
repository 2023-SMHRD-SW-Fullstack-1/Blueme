/*
작성자: 이지희
날짜(수정포함): 2023-09-22
설명: 음악 플레이리스트 리덕스 파일 추가 , musicStore 삭제(0922)
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
// 지희 끝

//유영 시작
  // In your store.js file 
  import { PersistGate } from 'redux-persist/integration/react';
  import { store, persistor }from './store/rootReducer'; 
  
//유영 끝

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <React.StrictMode>
  <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <App />
      </PersistGate>
  </Provider>

  // </React.StrictMode>
);

reportWebVitals();
