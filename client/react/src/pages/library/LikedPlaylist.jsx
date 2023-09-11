/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 라이브러리 페이지 내 좋아요 누른 곡 리스트 
*/

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

// import Component
import SingleMusic from "../../components/Library/SingleMusic";
// import DummyData
import PlaylistDummy from "../../dummy/PlaylistDummy.json";
// import Swiper
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";

const LikedPlaylist = () => {
  // 임의의 사용자 아이디
  let userId = 1;

  const [likedMusics, setLikedMusics] = useState([]);

  useEffect(() => {
    const fetchLikedList = async () => {
      try {
        const response = await axios.get(`/likemusics/${userId}`);
        let musicArray = response.data.map((item) => ({
          id: item.musicId,
          title: item.title,
          artist: item.artist,
          img: item.img,
        }));
        setLikedMusics(musicArray);
      } catch (error) {
        console.error("좋아요 목록 불러오기 실패", error);
      }
    };
    fetchLikedList();
  }, [userId]);

  return (
    <div className="bg-custom-blue text-custom-white h-full pt-20">
      {/* 플레이리스트 */}
      <div className="flex flex-col items-center justify-center">
        <img
          src={PlaylistDummy[0].coverImage}
          className="w-[200px] h-auto rounded-lg"
        />
        <p className="text-2xl py-5">{PlaylistDummy[0].title}</p>
      </div>
      <div className="flex justify-between ml-4 mr-4 py-2">
        <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">
          전체 재생
        </button>
        <Link>
          <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">
            전체 저장
          </button>
        </Link>
      </div>
      {/* 음악 데이터 */}
      <Swiper
        direction={"vertical"}
        slidesPerView={6}
        spaceBetween={1}
        className="h-[50%] ml-3 mr-3"
      >
        {likedMusics.map((music) => (
          <SwiperSlide key={music.id}>
            <SingleMusic item={music} />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default LikedPlaylist;
