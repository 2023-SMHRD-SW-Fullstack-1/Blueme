/*
작성자: 신지훈
날짜: 2023-09-16  
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 구현, 음악 재생 시간 오류 디버깅
*/
/*
작성자: 신지훈
날짜: 2023-09-21
설명: 리덕스 musicIds 설정
*/

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import SingleMusic from "../../components/Library/SingleMusic";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";
import axios from "axios";
import { setMusicIds } from "../../store/music/musicActions.js";

const SavedPlaylistDetail = () => {
  const dispatch = useDispatch();

  const [selectedPlaylistDetails, setSelectedPlaylistDetails] = useState([]);
  const [playlistImage, setPlaylistImage] = useState("");
  const [title, setTitle] = useState("");
  // musicIds 설정
  const [ids, setIds] = useState([]);

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
        if (response.data.savedMusiclistDetailsResDto.length > 0) {
          setPlaylistImage(response.data.savedMusiclistDetailsResDto[0].img);
        }
        console.log("response.data", response.data.savedMusiclistDetailsResDto);
        setIds(response.data.savedMusiclistDetailsResDto.map((music) => music.musicId));
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylistDetails();
  }, [id]);

  const handleListClick = () => {
    dispatch(setMusicIds(ids));
  };

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-full text-custom-white p-3 hide-scrollbar overflow-auto mb-[70px]">
      <br />
      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + playlistImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{title}</p>
      </div>
      <div className="mt-[20px]" onClick={handleListClick}>
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
