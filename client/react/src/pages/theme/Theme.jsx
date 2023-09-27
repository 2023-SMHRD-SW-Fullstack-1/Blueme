/*
작성자: 신지훈
날짜: 2023-09-22
설명: 테마 버그 및 세부 디자인 수정 , 반응형 구현 , 모바일 크기 조정
*/
/*
작성자: 이유영
날짜: 2023-09-27
설명: 테마 테그별 필터링
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

    // console.log("Stored in local storage:");
    // console.log("Theme image:", localStorage.getItem("themeImage"));
  };

  return (
    <button class="flex flex-col rounded-xl shadow-md mt-4 mb-4" onClick={handleButtonClick}>
      <Link to="/ThemePlaylist" className="">
        <div class="flex flex-col items-center justify-center text-center text-custom-white ">
          <img
            src={"data:image/;base64," + themeImage}
            alt=""
            class="rounded-sm w-[155px]  h-[145px] object-cover mt-sm "
          />
          <h5 class="text-sm mt-3 w-[80%]">{themeName}</h5>
        </div>
      </Link>
    </button>
  );
};

const Theme = () => {
  const [themes, setThemes] = useState([]);
  const [filterTheme, setFilterTheme] = useState([])

  // API 호출을 통해 테마 데이터 가져오기
  useEffect(() => {
    const fetchThemes = async () => {
      try {
        // axios.get(`${process.env.REACT_APP_API_BASE_URL}/your-endpoint`);
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/theme/themelists`);
        setThemes(response.data);
        // console.log(response);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };
    fetchThemes();
  }, []);
  
  const handleCommute = () => {
   const filter = themes.filter((theme) => (theme.tag === "출퇴근"))
   setFilterTheme(filter)
  }

  const handleFall = () => {
    const filter = themes.filter((theme) => theme.tag === "가을")
    setFilterTheme(filter)
  }

  const handleLonging  = () => {
    const filter = themes.filter((themes) => themes.tag === "그리움")
    setFilterTheme(filter)
  }

  const handleCool = () => {
    const filter = themes.filter((theme) => (theme.tag === "선선한"))
    setFilterTheme(filter)
  }

  return (
    <div className=" item-center justify-center overflow-auto hide-scrollbar min-h-screen mb-auto bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-byte text-custom-white font-semibold ">
      <p className="text-center pt-10 sm:pt-20 text-xl sm:text-3xl mt-20"></p>
      <div className="flex item-center justify-center ml-2">
      <button 
      onClick={handleCommute}
      className="mr-5 mb-3 hover:bg-gray-500 focus:outline-none focus:ring-2 focus:ring-white focus:ring-opacity-50 bg-gradient-to-t from-gray-600 rounded-lg text-sm h-8 w-20 p-1">
              # 출퇴근
      </button>
      <button 
      onClick={handleFall}
      className="mr-5 hover:bg-gray-500 focus:outline-none focus:ring-2 focus:ring-white focus:ring-opacity-50 bg-gradient-to-t from-gray-600 rounded-lg text-sm h-8 w-20 p-1">
              # 가을
      </button>
      <button 
      onClick={handleLonging}
      className="mr-5 hover:bg-gray-500 focus:outline-none focus:ring-2 focus:ring-white focus:ring-opacity-50 bg-gradient-to-t from-gray-600 rounded-lg text-sm h-8 w-20 p-1">
              # 그리움
      </button>
      <button 
      onClick={handleCool}
      className="mr-5 hover:bg-gray-500 focus:outline-none focus:ring-2 focus:ring-white focus:ring-opacity-50 bg-gradient-to-t from-gray-600 rounded-lg text-sm h-8 w-20 p-1">
              # 선선한
      </button>
      </div>
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-x-1 gap-y-1 sm:gap-x-1 sm:gap-y-1 lg:gap-x-0 lg:gap-y-1 mt-[10px] mb-[170px] xs:mb-[130px] text-custom-black mx-auto max-w-screen-lg justify-items-center">
        {/* 각각의 테마에 대해 ThemeCard 컴포넌트를 생성 */}
        {filterTheme.length == 0 ? 
        themes.map((theme, i) => (
          <ThemeCard key={i} theme={theme} index={i} />
        )) : filterTheme.map((theme, i) => (
          <ThemeCard key={i} theme={theme} index={i} />
        ))}
        <div className="mb-8"></div>
      </div>
    </div>
  );
};

export default Theme;
