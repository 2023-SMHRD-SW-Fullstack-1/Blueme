/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 라이브러리 페이지 내 좋아요 누른 곡 리스트 
*/

import React, { useState } from "react";
import { Link } from "react-router-dom";
import Heart from "./Heart";

const SingleMusic = ({ item }) => {
  // console.log('single',item);
  // console.log('single music', item.musicId);

  let musicId = item.musicId;
  
  return (
    
    <div className="flex flex-row items-center ml-2 mr-2">
      <Link to={`/MusicPlayer/${musicId}`}>
      <div className="flex flex-row items-center w-full p-1">
        <img
          src={"data:image/;base64," + item.img}
          className="w-[55px] h-[55px] rounded-md"
        />
        <div className="flex flex-col text-left ml-3">
          <span className="text-[18px] font-semibold">{item.title}</span>
          <span className="text-sm font-normal">{item.artist}</span>
        </div>
        <div className="flex-grow"></div>
      </div>
    </Link>
    <div className="ml-auto">
      {/* 하트 컴포넌트 */}
    {/* {item.musicId && ( */}
      <Heart item={item.musicId} />
    {/* )} */}
    </div>
    
  </div>
    
  );
};

export default SingleMusic;
