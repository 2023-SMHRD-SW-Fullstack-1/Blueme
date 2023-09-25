/*
작성자: 신지훈
날짜: 2023-09-22
설명: 반응형, 저장한 플레이리스트 불러오기 구현, 추가 버그 수정 
*/

import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Link } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";

const SavedPlaylist = () => {
  const [savedPlaylists, setSavedPlaylists] = useState([]);
  const [selectedPlaylistDetails, setSelectedPlaylistDetails] = useState([]);
  const [musiclistId, setMusiclistId] = useState();

  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  useEffect(() => {
    const fetchSavedPlaylists = async () => {
      try {
        const response = await axios.get(`http://172.30.1.27:8104/savedMusiclist/get/${userId}`);
        console.log("response", response.data);
        setSavedPlaylists(response.data);
        setMusiclistId(response.data.savedMusiclistId);
        // console.log(musiclistId);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylists();
  }, []);

  const handlePlaylistClick = async (id) => {
    console.log(id);
    try {
      const response = await axios.get(`http://172.30.1.27:8104/savedMusiclist/get/detail/${id}`);
      setSelectedPlaylistDetails(response.data);
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  useEffect(() => {
    console.log(musiclistId);
  }, [musiclistId]);

  const slidesPerView = savedPlaylists.length <= 2 ? savedPlaylists.length : 3;

  return (
    <div className="overflow-hidden indent-2 text-xl tracking-tight mt-3 lg:mt-10">
      <Swiper
        spaceBetween={10}
        slidesPerView={slidesPerView}
        breakpoints={{
          320: {
            slidesPerView: 2,
            spaceBetween: 10,
          },

          480: {
            slidesPerView: 2,
            spaceBetween: 10,
          },

          640: {
            slidesPerView: 2,
            spaceBetween: 10,
          },

          768: {
            slidesPerView: 2,
            spaceBetween: 10,
          },
          1024: {
            slidesPerView: 3,
            spaceBetween: 10,
          },
        }}
      >
        {savedPlaylists.map((SavedPlaylist) => (
          <SwiperSlide
            key={SavedPlaylist.savedMusiclistId}
            onClick={() => handlePlaylistClick(SavedPlaylist.savedMusiclistId)}
          >
            <Link to={`/SavedPlaylistDetail/${SavedPlaylist.savedMusiclistId}`}>
              <div className="flex flex-col justify-center items-center ml-2 mr-2 lg:w-[350px] ">
                {/* 1. 앨범 이미지 */}
                <img
                  src={"data:image/;base64," + SavedPlaylist.img}
                  alt="album cover"
                  className="w-[200px]  h-auto rounded-lg  sm:h-auto lg:mr-20"
                />
                {/* 2. 제목/ 아티스트 */}
                <span className="tracking-tighter text-sm text-center mt-2 lg:text-base lg:mr-20 ">
                  {SavedPlaylist.title}
                </span>
              </div>
            </Link>
          </SwiperSlide>
        ))}
      </Swiper>
      <div className="xs:mb-32 lg:mb-12 "></div>
    </div>
  );
};

export default SavedPlaylist;
