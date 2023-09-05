import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Main from './pages/Main';
import Join from './pages/join/Join';
import Login from './pages/Login';
import Footer from './components/Layout/Footer';
import Header from './components/Layout/Header';
import MyPage from './pages/myPage/MyPage';
import MemberInfo from './pages/myPage/MemberInfo';
import SelectArtist from './pages/join/SelectArtist';
import Theme from './pages/Theme';
import MemberInfoChange from './pages/myPage/MemberInfoChange';
import JoinComplete from './pages/join/JoinComplete';
import Library from './pages/Library';
import MusicPlayer from './pages/MusicPlayer';
import './styles/Tailwind.css';
import './assets/fonts/font.ttf';
import AddMusicPage from './pages/admin/AddMusicPage';
import Playlist from './pages/Playlist';
import SelectGenre from "./pages/join/SelectGenre";

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
                    <Route path="/Memberinfochange" element={<MyPage />} />
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
                </Routes>
                <Footer />
            </div>
        </Router>
    );
};

export default App;
