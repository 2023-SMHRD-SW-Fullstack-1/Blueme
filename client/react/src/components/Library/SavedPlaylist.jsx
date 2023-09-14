/*
작성자: 신지훈
날짜: 2023-09-13
설명: 반응형 구현
*/

import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import dummy from "../../dummy/MusicDummy.json";

const SavedPlaylist = () => {
  return (
    <div className="flex mt-5">
      <Swiper
        spaceBetween={0}
        slidesPerView={2.5}
        breakpoints={{
          768: {
            slidesPerView: 7,
            spaceBetween: 0,
          },
        }}
      >
        {dummy.map((item) => {
          return (
            <SwiperSlide key={item.id}>
              <div className="flex flex-col justify-center items-center ml-2 mr-2 w-50 ">
                {/* 1. 앨범 이미지 */}
                <img src={item.coverImage} alt="album cover" className="w-[180px] h-auto rounded-lg" />
                {/* 2. 제목/ 아티스트 */}
                <span className="tracking-tighter text-sm text-center mt-0">닉네임님의 플레이리스트</span>
              </div>
            </SwiperSlide>
          );
        })}
      </Swiper>
    </div>
  );
};

export default SavedPlaylist;
