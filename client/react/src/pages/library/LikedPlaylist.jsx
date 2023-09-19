/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: MusicIds 수정
*/

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";

// import Component
import SingleMusic from "../../components/Library/SingleMusic";
// import DummyData
import PlaylistDummy from "../../dummy/PlaylistDummy.json";
// import Swiper
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.css";



const LikedPlaylist = () => {
  // 사용자 user_id
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id

  const [ids, setIds] = useState([]);
  const [likedMusics, setLikedMusics] = useState([]);

  const dispatch = useDispatch();


useEffect(() => {
  const fetchLikedList = async () => {
    try { 
      const response = await axios.get(`/likemusics/${userId}`);
      console.log('더보기 response : ', response.data);
      setLikedMusics(response.data);
      setIds(response.data.map(music => music.musicId))
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  fetchLikedList();
}, []);

const handleListClick = () => {
  dispatch(setMusicIds(ids));
};


  return (
    <div className="bg-custom-blue text-custom-white h-full pt-20" onClick={handleListClick}>
      {/* 플레이리스트 */}
      <div className="flex flex-col items-center justify-center">
        <p className="text-3xl py-5">내가 좋아요 누른 곡</p>
      </div>
      <div className="flex justify-between ml-4 mr-4 py-2">
        <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">
          랜덤 재생
        </button>
        <Link>
          {/* <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">
            전체 저장
          </button> */}
        </Link>
      </div>
      {/* 음악 데이터 */}
      <Swiper
        direction={"vertical"}
        slidesPerView={8}
        spaceBetween={1}
        className="h-[70%] ml-3 mr-3"
      >
        {likedMusics.map((song) => (
          <SwiperSlide key={song.id}>
            <SingleMusic item={song} />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default LikedPlaylist;
