import React, { useRef } from "react";
import { Link, useLocation } from "react-router-dom";
import gpt from "../../assets/img/gpt.png";
import home from "../../assets/img/home.png";
import tag from "../../assets/img/tag.png";
import library from "../../assets/img/library.png";
import recommendAnimationData from "../../assets/img/recommend3.json";
import Lottie from "react-lottie";
import Search from "../../assets/img/search.png";

function Footer() {
  //musicplayer에서 footer숨기기 => 유영 추가
  const locationNow = useLocation();
  if (locationNow.pathname === "/MusicPlayer") return null;

  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: recommendAnimationData,
    rendererSettings: {
      preserveAspectRatio: "xMidYMid slice",
    },
  };
  return (
    <div
      className="text-custom-white p-3 sm:p-4 
        flex justify-between items-center absolute w-full h-[70px] bottom-1"
    >
      <Link to="/" className="flex flex-col items-center w-full text-center">
        <img src={home} className="h-[39px] w-[45px] mt-[4px]"></img>
        <p className="text-sm h-[15px]">홈</p>
      </Link>
      <Link to="/" className="flex flex-col items-center w-full text-center">
        <img src={Search} className="h-[35px] w-[35px] mt-[8px]"></img>
        <p className="text-sm h-[15px]">검색</p>
      </Link>
      {/* 지훈 - 검색 , 추천 애니메이션 추가  (0912) */}
      <Link to="/RecBegin" className="flex flex-col items-center w-full text-center mt-4">
        <Lottie options={defaultOptions} height={70} width={70} />
        <p className="text-sm h-[15px]"></p>
      </Link>

      {/* 지희 - 연결변경 to Theme.jsx (0908) */}
      <Link to="/Theme" className="flex flex-col items-center w-full text-center">
        <img src={tag} className="w-[33px] h-[35px] mt-[7px] mb-[2px]"></img>
        <button className="text-sm h-[15px]">테마</button>
      </Link>
      <Link to="/library" className="flex flex-col items-center w-full text-center">
        <img src={library} className="w-[35px] h-[39px] mt-[4px] mb-[4px]"></img>
        <button className="text-sm h-[15px]">라이브러리</button>
      </Link>
    </div>
  );
}

export default Footer;
