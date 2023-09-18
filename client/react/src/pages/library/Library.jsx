/*
작성자: 이지희
날짜(수정포함): 2023-09-16
설명: 사용자 라이브러리
*/

import React from "react";
import { Link } from "react-router-dom";
import LikedList from "../../components/Library/LikedList";
import SavedPlaylist from "../../components/Library/SavedPlaylist";

const Library = () => {
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-2 h-full font-semibold tracking-tighter">
      <br />
      <div className="flex mt-[90px] mb-3">
        <span className="text-left text-2xl ml-2">좋아요 누른 음악 목록</span>
        <button className="ml-auto text-custom-gray mr-2 text-sm">
          <Link to="/LikedPlaylist">더보기</Link>
        </button>
      </div>
      <LikedList />
      <p className="text-left text-2xl ml-2 pt-10">저장한 플레이리스트</p>
      <SavedPlaylist />
    </div>
  );
};

export default Library;
