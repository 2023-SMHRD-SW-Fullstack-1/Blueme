import React, { useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Main from "./pages/main/Main";
import Join from "./pages/join/Join";
import Login from "./pages/Login";
import Footer from "./components/Layout/Footer";
import Header from "./components/Layout/Header";
import MyPage from "./pages/myPage/MyPage";
import MemberInfo from "./pages/myPage/MemberInfo";
import SelectArtist from "./pages/join/SelectArtist";
import Theme from "./pages/Theme";
import MemberInfoChange from "./pages/myPage/MemberInfoChange";
import JoinComplete from "./pages/join/JoinComplete";
import Library from "./pages/Library";
import MusicPlayer from "./pages/MusicPlayer";
import "./styles/Tailwind.css";
import "./assets/fonts/font.ttf";
import AddMusicPage from "./pages/admin/AddMusicPage";
import Playlist from "./pages/Playlist";
import SelectGenre from "./pages/join/SelectGenre";
import DashBoard from "./pages/admin/DashBoard";

//
/* 지훈 시작 */
import MemberDelete from "./pages/myPage/MemberDelete";
/* 지훈 끝 */

// 유영 추천 앱 설명
import RecAppDes from "./pages/rec/RecAppDes";
import PlaylistRename from "./pages/rec/PlaylistRename";
//유영 끝

// 지희 import 시작
import RecBegin from "./pages/rec/RecBegin";
import LoadData from "./pages/rec/LoadData";
import LoadDataCompl from "./pages/rec/LoadDataCompl";
import LoadGpt from "./pages/rec/LoadGpt";
import RecPlayList from "./pages/rec/RecPlayList";
// 지희 import 끝

const App = () => {
  return (
    <Router>
      <div className="app">
        <Header />
        {/* Routes와 Route를 사용하여 경로에 따라 다른 페이지 렌더링 */}
        <Routes className="">
          {/* path 속성은 주어진 경로가 정확히 일치할 때만 해당 Route가 작동하도록 합니다 */}
          {/* 예: /testtest 경로는 Main과 일치하지 않으므로 Main은 렌더링되지 않습니다 */}
          <Route path="/" element={<Main />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Join" element={<Join />} />
          <Route path="/Memberinfo" element={<MemberInfo />} />
          <Route path="/MemberinfoChange" element={<MyPage />} />
          <Route path="/Artistrecommend" element={<SelectArtist />} />
          <Route path="/Themerecommend" element={<Theme />} />
          <Route path="/MemberInfoChange" element={<MemberInfoChange />} />
          <Route path="/JoinComplete" element={<JoinComplete />} />
          <Route path="/Library" element={<Library />} />
          {/* 필요한 경우 추가 Route 설정 */}
          <Route path="/addMusic" element={<AddMusicPage />} />
          {/* 라이브러리, 음악 재생 관련 */}
          <Route path="/MusicPlayer" element={<MusicPlayer />} />
          <Route path="/Playlist" element={<Playlist />} />
          <Route path="/SelectGenre" element={<SelectGenre />} />
          {/* 혁 추가 */}
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/addmusic" element={<AddMusicPage />} />
          {/* 혁 끝 */}
          <Route path="/Theme" element={<Theme />} />
          {/* 지훈 시작 */}
          <Route path="/MemberDelete" element={<MemberDelete />} />
          <Route path="/MyPage" element={<MyPage />} />
          {/* 지훈 끝 */}
          {/* 유영 시작 */}
          <Route path="/RecAppDes" element={<RecAppDes />} />
          <Route path="/PlaylistRename" element={<PlaylistRename />} />
          <Route path="/RecPlayList" element={<RecPlayList />} />
          {/* 유영 끝 */}
          {/* 지희 시작 */}
          <Route path="/RecBegin" element={<RecBegin />} />
          <Route path="/LoadData" element={<LoadData />} />
          <Route path="/LoadDataCompl" element={<LoadDataCompl />} />
          <Route path="/LoadGpt" element={<LoadGpt />} />
          {/* 지희 끝 */}
        </Routes>
        <Footer />
      </div>
    </Router>
  );
};

export default App;
