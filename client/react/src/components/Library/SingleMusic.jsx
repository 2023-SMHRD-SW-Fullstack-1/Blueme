/*
작성자: 이지희
날짜(수정포함): 2023-09-22
설명: 라이브러리 페이지 내 좋아요 누른 곡 리스트 
      0920 - navigate 설정
      0922 - 음악플레이어 이동 onclikc함수 수정
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-25
설명: 장르, 음악 재생 시간 위치 조정 및 반응형 추가, 좋아요 버그 수정
*/

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import Heart from "./Heart";

import { setCurrentSongId, setPlayingStatus } from "../../store/music/musicActions";

const SingleMusic = ({ item }) => {
  // console.log("single", item);

  const musicId = item.musicId;
  const currentSongId = useSelector((state) => state.musicReducer.currentSongId);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleMusicClick = () => {
    dispatch(setCurrentSongId(musicId));
    dispatch(setPlayingStatus(true));
    navigate(`/MusicPlayer/${musicId}`);
  };

  function convertSecondsToMinutes(time) {
    if (isNaN(time)) {
      return null;
    }

    const minutes = Math.floor(time / 60);
    const seconds = time % 60;

    return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  }

  return (
    <div
      className="flex flex-row items-center ml-2 mr-2 justify-between flex-grow  cursor-pointer"
      onClick={handleMusicClick}
    >
      <div className="flex flex-row items-center p-1 flex-grow">
        <img src={"data:image/;base64," + item.img} className="w-[55px] h-[55px] rounded-md" />
        <div className="flex flex-col text-left ml-3">
          <p className="md:text-lg font-semibold xs:text-sm xs:pb-1 overflow-hidden overflow-ellipsis whitespace-nowrap max-w-[180px]">
            {item.title}
          </p>
          <span className="text-xs font-normal">{item.artist}</span>
        </div>
      </div>

      {/* 고정 너비를 가진 컨테이너 */}
      <div className="w-[40%] hidden md:block text-custom-gray font-normal" onClick={handleMusicClick}>
        <span>{item.genre1}</span>
      </div>

      <div className="w-[20%] hidden md:block text-custom-gray font-normal">
        {item.time && <span>{convertSecondsToMinutes(item.time)}</span>}
      </div>

      {/* 하트 컴포넌트 */}
      <div className="ml-auto flex-shrink-0">
        <Heart item={item.musicId} />
      </div>
    </div>
  );
};

export default SingleMusic;
