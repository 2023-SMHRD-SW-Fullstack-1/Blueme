import React from "react";

import Main from "./components/Main";
import Join from "./components/Member/Join";
import Login from "./components/Member/Login";
import Footer from "./components/Layout/Footer";
import Header from "./components/Layout/Header";
import Memberinfochange from "./components/Member/Memberinfochange";
import Memberinfo from "./components/Member/Memberinfo";
import Artistrecommend from "./components/Artistrecommend";
import JoinComplete from "./components/Member/JoinComplete";
import Themerecommend from "./components/Themerecommend";
import Myprofile from "./components/Member/Myprofile";

import "./Tailwind.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./Fonts/font.ttf";

const App = () => {
  return (
    <Router>
      <div className="">
        <Header />

        {/* Routes와 Route를 사용하여 경로에 따라 다른 페이지 렌더링 */}
        <Routes className="">
          {/* path 속성은 주어진 경로가 정확히 일치할 때만 해당 Route가 작동하도록 합니다 */}
          {/* 예: /testtest 경로는 Main과 일치하지 않으므로 Main은 렌더링되지 않습니다 */}
          <Route path="/" element={<Main />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Join" element={<Join />} />
          <Route path="/Memberinfo" element={<Memberinfo />} />
          <Route path="/Memberinfochange" element={<Memberinfochange />} />
          <Route path="/Artistrecommend" element={<Artistrecommend />} />
          <Route path="/JoinComplete" element={<JoinComplete />} />
          <Route path="/Themerecommend" element={<Themerecommend />} />
          <Route path="/Myprofile" element={<Myprofile />} />
          {/* 필요한 경우 추가 Route 설정 */}
        </Routes>

        <Footer />
      </div>
    </Router>
  );
};

export default App;
