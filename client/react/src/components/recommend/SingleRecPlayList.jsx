/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 싱글 컴포넌트
*/
import React from "react";
import Heart from '../Library/Heart'


const SingleRecPlayList = ({ item }) => {




  return (
    
    <div className="flex flex-row items-center ml-2 mr-2 justify-between">
    
      <div className="flex flex-row items-center  p-1 mt-2">
        <img
        src={"data:image/;base64," + item.img}
        alt="no img"
        className="w-[50px] h-[50px] rounded"
        />
        
        <div className="flex flex-col text-left ml-3">
          <span className="text-[15px] w-[250px] h-[25px] overflow-hidden">{item.musicTitle}</span>
          <span className="text-xs font-normal">{item.musicArtist}</span>
        </div>

        <div className="flex-grow"></div>
      </div>
       {/* 하트 컴포넌트 */}
       <div className="ml-auto">
        <Heart key={item.id} item={item.musicId} />
      </div>
    </div>
    
  );
};

export default SingleRecPlayList;
