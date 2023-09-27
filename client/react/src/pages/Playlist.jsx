/*
작성자: 신지훈
날짜: 2023-09-11
설명: 단일 테마리스트 상세 조회
*/

// 일단 보존

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import SingleMusic from "../components/Library/SingleMusic";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";
import axios from "axios";

const Playlist = () => {
  const [themeImage, setThemeImage] = useState("");
  const [themeName, setThemeName] = useState("");
  const [musicList, setMusicList] = useState([]);

  useEffect(() => {
    const getPlaylistDetails = async () => {
      try {
        const imageFromStorage = localStorage.getItem("themeImage");
        if (imageFromStorage) {
          setThemeImage(imageFromStorage);
        }

        const nameFromStorage = localStorage.getItem("themeName");
        if (nameFromStorage) {
          setThemeName(nameFromStorage);
        }

        const themeIdFromStorage = localStorage.getItem("themeId");

        if (themeIdFromStorage) {
          const responseMusicList = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/theme/themelists/${themeIdFromStorage}`);

          if (responseMusicList.data) {
            setMusicList(responseMusicList.data);
          }
        }
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    getPlaylistDetails();
  }, []);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <br />
      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + themeImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{themeName}</p>
      </div>

      <div className="flex justify-between mb-5 mt-2">
        <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
          전체 재생
        </button>
        <button className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
          전체 저장
        </button>
      </div>

      {/* Render music list */}
      {musicList.length > 0 ? (
        <Swiper direction={"vertical"} slidesPerView={6.2} spaceBetween={1} className="h-[49%] ml-3 mr-3">
          {musicList.map((item) => (
            <SwiperSlide key={item.id}>
              <SingleMusic key={item.id} item={item} />
            </SwiperSlide>
          ))}
        </Swiper>
      ) : (
        <p>No music available</p>
      )}
    </div>
  );
};

export default Playlist;
