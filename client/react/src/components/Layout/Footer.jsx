/*
작성자: 신지훈
날짜: 2023-09-20
설명: Footer 세부 조정
*/

import React from "react";
import { Link, useLocation } from "react-router-dom";

import home from "../../assets/img/home.png";
import tag from "../../assets/img/tag.png";
import library from "../../assets/img/library.png";
import recommendAnimationData from "../../assets/img/recommend5.json";
import Lottie from "lottie-react";
import Search from "../../assets/img/search.png";

function Footer() {
  //musicplayer에서 footer숨기기 => 유영 추가
  const locationNow = useLocation();
  if (locationNow.pathname === "/MusicPlayer") return null;

  const Lottiestyle = {
    weight: 100,
    height: 100,
  };
  return (
    <div
      className="text-custom-white p-3 sm:p-4 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700
    flex justify-between items-center fixed bottom-0 w-full h-[70px] xs:z-50"
    >
      <Link to="/" className="flex flex-col items-center w-full text-center">
        <img src={home} className="h-[35px] w-[35px] mt-[4px]"></img>
        <p className="text-sm h-[15px]">홈</p>
      </Link>
      <Link to="/search" className="flex flex-col items-center w-full text-center">
        <img src={Search} className="h-[30px] w-[30px] mt-[8px]"></img>
        <p className="text-sm h-[15px]">검색</p>
      </Link>
      {/* 지훈 - 검색 , 추천 애니메이션 추가  (0913) */}
      <Link to="/RecBegin" className="flex flex-col items-center w-full text-center mt-4">
        <Lottie animationData={recommendAnimationData} style={Lottiestyle} />
        <p className="text-sm h-[15px]"></p>
      </Link>

      {/* 지희 - 연결변경 to Theme.jsx (0908) */}
      <Link to="/Theme" className="flex flex-col items-center w-full text-center">
        <img src={tag} className="w-[30px] h-[30px] mt-[7px] mb-[2px]"></img>
        <button className="text-sm h-[15px]">테마</button>
      </Link>
      <Link to="/library" className="flex flex-col items-center w-full text-center">
        <img src={library} className="w-[30px] h-[30px] mt-[4px] mb-[4px]"></img>
        <button className="text-sm h-[15px]">라이브러리</button>
      </Link>
    </div>
  );
}

export default Footer;
