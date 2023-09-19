/*
작성자: 이지희
날짜(수정포함): 2023-09-16
설명: 사용자 라이브러리
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-18
설명: 사용자 라이브러리 반응형 진행중
*/

import React from "react";
import { Link } from "react-router-dom";
import LikedList from "../../components/Library/LikedList";
import SavedPlaylist from "../../components/Library/SavedPlaylist";

const Library = () => {
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-2 h-full font-semibold tracking-tighter">
      <br />
      <div className="flex flex-col md:flex-row">
        <div className="w-full md:w-1/2 pr-0 md:pr-2 order-last md:order-first lg:pr-10 ">
          <p className="text-left text-xl ml-[5px] pt-[20px] lg:pt-[100px] lg:text-3xl">저장한 플레이리스트</p>
          <SavedPlaylist />
        </div>

        <div className="w-full md:w-1/2 pl-0 md:pl-2 mt-[30px] md:mt-[10px] order-first md:order-last  lg:pr-10">
          <div className="flex mt-14 xs:mt-5 mb-3">
            <span className="text-left text-xl ml-[5px]  lg:pt-[70px] lg:text-3xl lg:mb-5 ">좋아요 누른 음악 목록</span>
            <button className="ml-auto text-custom-gray mr-2 text-sm">
              <Link to="/LikedPlaylist">더보기</Link>
            </button>
          </div>
          <LikedList />
        </div>
      </div>
    </div>
  );
};

export default Library;
