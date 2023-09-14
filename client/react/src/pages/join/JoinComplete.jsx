/*
작성자: 신지훈
날짜: 2023-09-04
설명: 회원가입 화면
*/

import React from "react";
import { Link } from "react-router-dom";

const JoinComplete = () => {
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold text-center tracking-tighter h-screen text-custom-white p-3">
      <p className="mt-[350px] text-[23px] mb-3">Blueme 회원가입을 축하드립니다.</p>
      <Link to="/Login">
        <span className="text-sm text-custom-gray pt-3">로그인하기</span>
      </Link>
    </div>
  );
};

export default JoinComplete;
