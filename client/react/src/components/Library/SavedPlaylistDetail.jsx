/*
작성자: 신지훈
날짜: 2023-09-16  
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 구현, 음악 재생 시간 오류 디버깅
*/

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SingleMusic from "../../components/Library/SingleMusic";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";
import axios from "axios";

const SavedPlaylistDetail = () => {
  const [selectedPlaylistDetails, setSelectedPlaylistDetails] = useState([]);
  const [playlistImage, setPlaylistImage] = useState("");
  const [title, setTitle] = useState("");

  let params = useParams();
  let id = params.id;
  function convertSecondsToMinutes(time) {
    if (isNaN(time)) {
      console.error("Invalid time value:", time);
      return "0:00";
    }

    const minutes = Math.floor(time / 60);
    const seconds = time % 60;

    return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
  }
  // 플레이리스트 내 음악리스트 정보
  useEffect(() => {
    const fetchSavedPlaylistDetails = async () => {
      try {
        const response = await axios.get(`http://172.30.1.27:8104/savedMusiclist/get/detail/${id}`);
        setSelectedPlaylistDetails(response.data.savedMusiclistDetailsResDto);
        setTitle(response.data.title);
        console.log(response.data);
        console.log(selectedPlaylistDetails);

        if (response.data.savedMusiclistDetailsResDto.length > 0) {
          setPlaylistImage(response.data.savedMusiclistDetailsResDto[0].img);
        }
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylistDetails();
  }, [id]);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-full text-custom-white p-3 hide-scrollbar overflow-auto mb-[70px]">
      <br />
      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + playlistImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{title}</p>
      </div>
      <div className="mt-[20px]">
        {selectedPlaylistDetails.map((music) => (
          <SingleMusic
            key={music.musicId}
            item={{
              musicId: music.musicId,
              img: music.img,
              title: music.musicTitle,
              artist: music.musicArtist,
              genre1: music.musicGenre,
              time: music.time,
            }}
          />
        ))}
      </div>
    </div>
  );
};

export default SavedPlaylistDetail;
