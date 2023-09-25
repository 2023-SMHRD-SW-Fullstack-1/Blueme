/*
작성자: 신지훈
날짜: 2023-09-22
설명: 테마 버그 및 세부 디자인 수정 , 반응형 구현 , 모바일 크기 조정
*/

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

// ThemeCard 컴포넌트 생성: 각 테마를 보여주는 카드
const ThemeCard = ({ theme, index }) => {
  const themeImage = theme?.img;
  const themeName = theme?.title;
  const themeId = theme?.id || index + 1;

  // 테마의 정보 로컬 스토리지 저장
  const handleButtonClick = () => {
    localStorage.setItem("themeImage", themeImage);
    localStorage.setItem("themeName", themeName);
    localStorage.setItem("themeId", themeId);

    console.log("Stored in local storage:");
    console.log("Theme image:", localStorage.getItem("themeImage"));
  };

  return (
    <button class="flex flex-col rounded-xl shadow-md mt-4 mb-4" onClick={handleButtonClick}>
      <Link to="/ThemePlaylist" className="">
        <div class="flex flex-col items-center justify-center text-center text-custom-white ">
          <img
            src={"data:image/;base64," + themeImage}
            alt=""
            class="rounded-lg w-[180px] xs:w-[150px] h-[170px] sm:w-[230px] sm:h-[230px] object-cover mt-sm "
          />
          <h5 class="text-sm mt-3 w-[80%]">{themeName}</h5>
        </div>
      </Link>
    </button>
  );
};

const Theme = () => {
  const [themes, setThemes] = useState([]);

  // API 호출을 통해 테마 데이터 가져오기
  useEffect(() => {
    const fetchThemes = async () => {
      try {
        const response = await axios.get("/theme/themelists");
        setThemes(response.data);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchThemes();
  }, []);

  return (
    <div className="overflow-auto hide-scrollbar min-h-screen mb-auto bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-byte text-custom-white font-semibold ">
      <p className="text-center pt-10 sm:pt-20 text-xl sm:text-3xl mt-10"></p>
      <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-x-1 gap-y-1 sm:gap-x-1 sm:gap-y-1 lg:gap-x-0 lg:gap-y-1 mt-[10px] mb-[90px] text-custom-black mx-auto max-w-screen-lg justify-items-center">
        {/* 각각의 테마에 대해 ThemeCard 컴포넌트를 생성 */}
        {themes.map((theme, i) => (
          <ThemeCard key={i} theme={theme} index={i} />
        ))}
        <div className="mb-8"></div>
      </div>
    </div>
  );
};

export default Theme;
