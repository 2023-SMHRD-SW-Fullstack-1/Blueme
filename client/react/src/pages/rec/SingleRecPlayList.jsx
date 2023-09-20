/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 플레이리스트 컴포넌트
*/
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Heart from "../../components/Library/Heart";
import { useSelector, useDispatch } from "react-redux";
import { setCurrentSongId } from "../../store/music/musicActions";

const SingleRecPlayList = ({ item }) => {
  // console.log('recSingle',item);
  // const user = useSelector(state => state.memberReducer.user)
  // const id = user.id
  // console.log("single", item);

  const musicId = item.musicId;
  // console.log(musicId);

  const dispatch = useDispatch()

  const handleMusicClick = () => {
    dispatch(setCurrentSongId(musicId));
  };



  
  return (
    
    <div className="flex flex-row items-center ml-2 mr-2 justify-between">
    <Link  to={`/MusicPlayer/${musicId}`} onClick={handleMusicClick} className="flex-grow">
      <div className="flex flex-row items-center  p-1 ">
      <img
       src={"data:image/;base64," + item.img}
       className="w-[55px] h-[55px] rounded-md"
       />
        <div className="flex flex-col text-left ml-3">
          <span className="text-[17px] w-[250px] h-[25px] overflow-hidden">{item.musicTitle}</span>
          <span className="text-sm font-normal">{item.musicArtist}</span>
        </div>
        <div className="flex-grow"></div>
      </div>
    </Link>
      {/* 하트 컴포넌트 */}
      <div className="ml-auto">
        <Heart key={item.id} item={item.musicId} />
      </div>
    </div>
    
  );
};

export default SingleRecPlayList;
