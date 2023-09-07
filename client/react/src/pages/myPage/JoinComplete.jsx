import React from "react";
import { Link } from "react-router-dom";

const JoinComplete = () => {
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      Blueme 회원가입을 축하드립니다.
      <Link to="/">
        <span className="text-sm text-custom-gray pt-3"> 홈 화면 이동</span>
      </Link>
    </div>
  );
};

export default JoinComplete;
