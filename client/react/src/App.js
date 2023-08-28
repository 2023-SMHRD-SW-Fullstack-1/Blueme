import React from "react";
import Header from "./components/Header";
import Footer from "./components/footer";
import Main from "./components/Main";
import Login from "./components/Login"; // TestTest 컴포넌트의 이름은 대문자로 시작해야 합니다.
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

const App = () => {
  return (
    <Router>
      <div>
        <Header />

        {/* Routes와 Route를 사용하여 경로에 따라 다른 페이지 렌더링 */}
        <Routes>
          {/* path 속성은 주어진 경로가 정확히 일치할 때만 해당 Route가 작동하도록 합니다 */}
          {/* 예: /testtest 경로는 Main과 일치하지 않으므로 Main은 렌더링되지 않습니다 */}
          <Route path="/" element={<Main />} />
          <Route path="/Login" element={<Login />} />

          {/* 필요한 경우 추가 Route 설정 */}
        </Routes>

        <Footer />
      </div>
    </Router>
  );
};

export default App;
