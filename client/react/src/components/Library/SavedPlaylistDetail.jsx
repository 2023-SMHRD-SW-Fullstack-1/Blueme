/*
작성자: 신지훈
날짜: 2023-09-08  
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 구현
*/

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SingleMusic from "../../components/Library/SingleMusic";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";
import axios from "axios";
import SavedPlaylist from "./SavedPlaylist";

const SavedPlaylistDetail = () => {
  const [savedPlaylists, setSavedPlaylists] = useState([]);
  const [selectedPlaylistDetails, setSelectedPlaylistDetails] = useState([]);

  let params = useParams();
  let id = params.id;

  // 저장한 플레이리스트 제목, 사진
  useEffect(() => {
    const fetchSavedPlaylists = async () => {
      try {
        const response = await axios.get("http://172.30.1.27:8104/savedMusiclist/get/1");
        // console.log("response", response.data);
        setSavedPlaylists(response.data);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylists();
  }, []);

  // 플레이리스트 내 음악리스트 정보
  useEffect(() => {
    const fetchSavedPlaylistDetails = async () => {
      try {
        const response = await axios.get(`http://172.30.1.27:8104/savedMusiclist/get/detail/${id}`);
        console.log("response.data", response.data);
        console.log("data", response.data.savedMusiclistDetailsResDto);
        // console.log("response", response.data);
        setSelectedPlaylistDetails(response.data.savedMusiclistDetailsResDto);
        // setMusiclistId();
        // console.log(musiclistId);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylistDetails();
  }, []);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      {/* <br />
      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + themeImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{themeName}</p>
      </div>

      <div className="flex justify-between mb-5 mt-2">
        <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
          전체 재생
        </button>
        <button
          onClick={saveMusicList}
          className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl"
        >
          전체 저장
        </button>
      </div> */}

      {/* Render music list */}
      {/* {musicList.length > 0 ? (
        <Swiper direction={"vertical"} slidesPerView={6.2} spaceBetween={1} className="h-[49%] ml-3 mr-3">
          {musicList.map((item) => (
            <SwiperSlide key={item.id}>
              <SingleMusic key={item.id} item={item} />
            </SwiperSlide>
          ))}
        </Swiper>
      ) : (
        <p>No music available</p>
      )} */}
      {selectedPlaylistDetails.map((detail) => (
        <div key={detail.savedMusiclistsDetailId}>
          {/* 음악 이미지 */}
          <img src={detail.musicAlbum} alt="music cover" />
          {/* 음악 제목 */}
          <p>{detail.musicTitle}</p>
          {/* 아티스트 */}
          <p>{detail.musicArtist}</p>
        </div>
      ))}
    </div>
  );
};

export default SavedPlaylistDetail;
