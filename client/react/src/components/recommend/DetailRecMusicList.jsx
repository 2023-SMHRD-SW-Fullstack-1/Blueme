/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 디테일 컴포넌트
*/
import React from "react";



const SingleRecPlayList = ({ item }) => {




  return (
    
    <div className="flex flex-row items-center ml-2 mr-2 justify-between">
      <div className="flex flex-row items-center p-1 mt-2">
        <img
        src={"data:image/;base64," + item.img}
        alt="music img"
        className="w-[50px] h-[50px] rounded"
        />
        
        <div className="flex flex-col text-left ml-3">
          <span className="text-[15px] w-[250px] h-[25px] overflow-hidden">{item.title}</span>
          <span className="text-xs font-normal">{item.artist}</span>         
        </div>
      </div>
    </div>
    
  );
};

export default SingleRecPlayList;
