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
          const responseMusicList = await axios.get(`/theme/themelists/${themeIdFromStorage}`);

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
    <div className="bg-custom-blue text-custom-white h-full pt-20">
      <div className="flex flex-col items-center justify-center">
        <img src={themeImage} className="w-[200px] h-auto rounded-lg" />
        <p className="text-2xl py-5">{themeName}</p>
      </div>

      <div className="flex justify-between ml-4 mr-4 py-2">
        <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">전체 재생</button>
        <Link>
          <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">전체 저장</button>
        </Link>
      </div>

      {/* Render music list */}
      {musicList.length > 0 ? (
        <Swiper direction={"vertical"} slidesPerView={6} spaceBetween={1} className="h-[50%] ml-3 mr-3">
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
