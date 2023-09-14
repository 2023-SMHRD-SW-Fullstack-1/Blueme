/*
작성자: 신지훈
날짜: 2023-09-08  
설명: 테마별 플레이리스트 화면, 불러오기
*/

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import SingleMusic from "../../components/Library/SingleMusic";

import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";

import axios from "axios";

const ThemePlaylist = () => {
  const [themeImage, setThemeImage] = useState("");
  const [themeName, setThemeName] = useState("");
  const [musicList, setMusicList] = useState([]);

  useEffect(() => {
    const getPlaylistDetails = async () => {
      try {
        // Get the image URL and theme name directly from local storage
        const imageFromStorage = localStorage.getItem("themeImage");
        if (imageFromStorage) {
          console.log(imageFromStorage);
          setThemeImage(imageFromStorage);
        }

        const nameFromStorage = localStorage.getItem("themeName");
        if (nameFromStorage) {
          setThemeName(nameFromStorage);
        }

        // Fetch the music list for this theme
        const themeIdFromStorage = localStorage.getItem("themeId");

        if (themeIdFromStorage) {
          const responseMusicList = await axios.get(`/theme/themelists/${themeIdFromStorage}`);
          console.log(responseMusicList);
          if (responseMusicList.data.data) {
            setMusicList(responseMusicList.data.data);
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
        <img src={themeImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{themeName}</p>
      </div>

      <div className="flex justify-between mb-5 mt-2">
        <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
          전체 재생
        </button>
        <Link>
          <button className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
            전체 저장
          </button>
        </Link>
      </div>

      {/* 렌더링된 음악 리스트 */}
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

export default ThemePlaylist;
