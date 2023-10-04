/*
작성자: 신지훈
날짜: 2023-09-20
설명: Footer 세부 조정
*/

import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

import home from "../../assets/img/home.png";
import tag from "../../assets/img/tag.png";
import library from "../../assets/img/library.png";
import recommendAnimationData from "../../assets/img/recommend5.json";
import Lottie from "lottie-react";
import Search from "../../assets/img/search.png";
import { useSelector } from "react-redux";




function Footer() {
  const navigate = useNavigate()
  const member = useSelector((state) => state.memberReducer);
  const isLogin = member.isLogin

  //musicplayer에서 footer숨기기 => 유영 추가
  const locationNow = useLocation();
  if (locationNow.pathname === "/MusicPlayer") return null;

  const Lottiestyle = {
    weight: 100,
    height: 100,
  };
  
   // 로딩 함수 => 유영 추가
   const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').style.display = "none"//토스트 창 소멸
      navigate("/Login");
    }, 1000);// 원하는 시간 ms단위로 적어주기
  };
 

  //로그인 여부 판단하는 함수 => 유영 추가
  const handleIsLogin = () => {
    if(isLogin) {
      navigate('/library')
    }else {
      document.getElementById('toast-warning').style.display = 'block'//토스트 창 생성
      timeout()
    }
  }
  
  return (
    <div
      className="text-custom-white p-3 sm:p-4 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700
    flex justify-between items-center fixed bottom-0 w-full h-[65px] xs:z-50"
    >
      <Link to="/" className="flex flex-col items-center w-full text-center">
        <img src={home} className="h-[35px] w-[41px] mt-[4px]"></img>
        <p className="text-xs h-[15px]">홈</p>
      </Link>
      <Link to="/search" className="flex flex-col items-center w-full text-center">
        <img src={Search} className="h-[31px] w-[31px] mt-[8px]"></img>
        <p className="text-xs h-[15px]">검색</p>
      </Link>
      {/* 지훈 - 검색 , 추천 애니메이션 추가  (0913) */}
      <Link to="/RecBegin" className="flex flex-col items-center w-full text-center mt-4">
        <Lottie animationData={recommendAnimationData} style={Lottiestyle} />
        <p className="text-xs h-[15px]"></p>
      </Link>

      {/* 지희 - 연결변경 to Theme.jsx (0908) */}
      <Link to="/Theme" className="flex flex-col items-center w-full text-center">
        <img src={tag} className="w-[29px] h-[31px] mt-[7px] mb-[2px]"></img>
        <button className="text-xs h-[15px]">테마</button>
      </Link>
      <div onClick={handleIsLogin} className="flex flex-col items-center w-full text-center">
        <img src={library} className="w-[31px] h-[34px] mt-[7px] mb-[0px]" ></img>
        <button className="text-xs h-[15px]">라이브러리</button>
      </div>
      {/* 토스트 창 띄우기 */}
      <div id="toast-warning" className="flex items-center border w-full fixed left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert" style={{display: 'none'}}>
        <div className="flex ml-[120px] mb-2 items-center justify-center w-8 h-8 text-orange-500 bg-orange-100 rounded-lg dark:bg-orange-700 dark:text-orange-200">
          <svg className="w-5 h-5 " aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
              <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM10 15a1 1 0 1 1 0-2 1 1 0 0 1 0 2Zm1-4a1 1 0 0 1-2 0V6a1 1 0 0 1 2 0v5Z"/>
          </svg>
          <span className="sr-only">Warning icon</span>
      </div>
      <div className="ml-3 font-normal text-center">로그인 후 이용해주세요.</div>
      </div>
    </div>
  );
}

export default Footer;