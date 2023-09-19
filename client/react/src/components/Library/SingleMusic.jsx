/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 라이브러리 페이지 내 좋아요 누른 곡 리스트 
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-18
설명: 장르, 음악 재생 시간 위치 조정 및 반응형 추가
*/

import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import Heart from "./Heart";

import { setCurrentSongId } from "../../store/music/musicActions";

const SingleMusic = ({ item }) => {
  console.log("single", item);

  const musicId = item.musicId;

  const dispatch = useDispatch();

  const handleMusicClick = () => {
    dispatch(setCurrentSongId(musicId));
  };

  function convertSecondsToMinutes(time) {
    const minutes = Math.floor(time / 60);
    const seconds = time % 60;

    return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  }

  return (
    <div className="flex flex-row items-center ml-2 mr-2 justify-between">
      <Link to={`/MusicPlayer/${musicId}`} onClick={handleMusicClick} className="flex-grow">
        <div className="flex flex-row items-center p-1">
          <img src={"data:image/;base64," + item.img} className="w-[55px] h-[55px] rounded-md" />
          <div className="flex flex-col text-left ml-3">
            <span className="text-[18px] font-semibold">{item.title}</span>
            <span className="text-sm font-normal">{item.artist}</span>
          </div>
        </div>
      </Link>

      {/* 고정 너비를 가진 컨테이너 */}
      <div className="w-[40%] hidden md:block text-custom-gray font-normal">
        <span>{item.genre1}</span>
      </div>

      <div className="w-[20%] hidden md:block text-custom-gray font-normal">
        <span>{convertSecondsToMinutes(item.time)}</span>
      </div>

      {/* 하트 컴포넌트 */}
      <div className="ml-auto">
        <Heart item={item.musicId} />
      </div>
    </div>
  );
};

export default SingleMusic;
