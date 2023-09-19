/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 플레이리스트 컴포넌트
*/
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Heart from "../../components/Library/Heart";
import { useSelector } from "react-redux";

const SingleMusic = ({ item }) => {
  // console.log('recSingle',item);
  const user = useSelector(state => state.memberReducer.user)
  const id = user.id
  // console.log('header',user);


  
  return (
    
    <div className="flex flex-row items-center ml-2 mr-2">
    <Link to={{ pathname: "/RecPlayList", search: `?url=http://172.30.1.27:8104/recMusiclist/${id}` }}>
      <div className="flex flex-row items-center w-full p-1 mb-1">
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
    <div className="ml-auto">
      {/* 하트 컴포넌트 */}
    {/* {item.musicId && ( */}
      <Heart key={item.id} item={item.musicId} />
    {/* )} */}
    </div>
    
  </div>
    
  );
};

export default SingleMusic;
