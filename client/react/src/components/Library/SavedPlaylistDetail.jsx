/*
작성자: 신지훈
날짜: 2023-09-16  
설명: 테마별 플레이리스트 화면, 불러오기, 전체 저장 구현
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
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchSavedPlaylistDetails();
  }, [id]);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <br />
      <div className="flex flex-col items-center justify-center mt-[80px]">
        <img src={"data:image/;base64," + playlistImage} className="w-[160px] h-[160px] rounded-xl" />
        <p className="text-2xl py-5">{title}</p>
      </div>

      {selectedPlaylistDetails.length > 0 ? (
        <Swiper direction={"vertical"} slidesPerView={6.2} spaceBetween={1} className="h-[49%] ml-3 mr-3">
          {selectedPlaylistDetails.map((item) => (
            <SwiperSlide key={item.musicId}>
              <SingleMusic item={{ ...item, title: item.musicTitle, artist: item.musicArtist }} />
            </SwiperSlide>
          ))}
        </Swiper>
      ) : (
        <p>No music available</p>
      )}
    </div>
  );
};

export default SavedPlaylistDetail;
