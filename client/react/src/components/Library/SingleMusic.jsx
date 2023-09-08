import React, { useState } from "react";
import { Link } from "react-router-dom";
// import - 이미지
import likeEmpty from "../../assets/img/likeEmpty.png";
const paramValue = "http://172.30.1.27:8104/music/1";

const SingleMusic = ({ item }) => {
  return (
    <Link
      to={{
        pathname: "/MusicPlayer",
        search: `?url=${paramValue}`,
      }}
    >
      <div className="flex flex-col items-center ml-2 mr-2">
        <div className="flex flex-row items-center w-full p-1">
          <img src={item.coverImage} className="w-[55px] h-[55px] rounded-md" />
          <div className="flex flex-col text-left ml-3">
            <span className="text-[18px] font-semibold">{item.title}</span>
            <span className="text-sm font-normal ">{item.artist}</span>
          </div>
          <div className="flex-grow"></div>
          <img src={likeEmpty} className="w-[25px] h-auto rounded-lg align" />
        </div>
      </div>
    </Link>
  );
};

export default SingleMusic;
