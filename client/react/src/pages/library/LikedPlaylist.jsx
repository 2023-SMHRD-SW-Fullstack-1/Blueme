/*
작성자: 이지희
날짜(수정포함): 2023-09-12
설명: 좋아요 누른 곡 전체리스트 (더보기 클릭 시)
*/

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/setMusicIds";

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

  const dispatch = useDispatch();
  const musicIds = useSelector(state => state);

  const [likedMusics, setLikedMusics] = useState([]);

useEffect(() => {
  const fetchLikedList = async () => {
    try { 
      const response = await axios.get(`/likemusics/${userId}`);
      console.log('더보기 response : ', response.data);
      setLikedMusics(response.data);
      let ids = response.data.map(music => music.musicId);
      console.log('더보기 ids', ids);
      dispatch(setMusicIds(ids));
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  fetchLikedList();
}, []);

// 리덕스에 저장됐는지 확인
  useEffect(() => {
    console.log('Updated music IDs:', musicIds);
  }, [musicIds]);

  return (
    <div className="bg-custom-blue text-custom-white h-full pt-20">
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
