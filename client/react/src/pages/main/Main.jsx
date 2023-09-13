/*
작성자: 신지훈
날짜: 2023-09-11
설명: 최근 재생목록 불러오기
*/
/*
작성자: 이지희
날짜(수정포함): 2023-09-12
설명: 음악 재생리스트 리덕스 기능 추가 , 최근 재생목록 최대개수 20개로 수정
*/
import React, { useEffect, useState } from "react";
import SavedPlaylist from "../../components/Library/SavedPlaylist";
import BeforeRegistration from "./BeforeRegistration";
import LikedList from "../../components/Library/LikedList";
import { Swiper, SwiperSlide } from "swiper/react";
import MusicDummy from "../../dummy/MusicDummy.json";
import SingleMusic from "../../components/Library/SingleMusic";
import { Link } from "react-router-dom";
import RecPlayList from "../rec/RecPlayList";
import axios from "axios";
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/setMusicIds";
// 미니플레이어 import
import MiniPLayer from "../MiniPlayer";

const Main = () => {
  const [recMusic, setRecMusic] = useState(null);
  const [recentlyPlayed, setRecentlyPlayed] = useState([]);

  const dispatch = useDispatch();
  const musicIds = useSelector(state => state);


  useEffect(() => {
    const fetchRecentlyPlayed = async () => {
      try {
        const userId = 1; // 현재 로그인한 사용자의 ID. 실제로는 인증 시스템을 통해 얻어야 합니다.
        const response = await axios.get(`/playedmusic/get/${userId}`);
        setRecentlyPlayed(response.data);
        let ids = response.data.slice(0, 20).map(music => music.musicId);
        dispatch(setMusicIds(ids));
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchRecentlyPlayed();
  }, []);

// 리덕스에 저장됐는지 확인
// useEffect(() => {
//    console.log('Updated music IDs:', musicIds);
//   }, [musicIds]);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full">
      <br />
      <br />
      {/* ChatGPT가 추천해준 나의 플레이리스트 */}
      <div className="py-2 flex justify-between">
        <h1 className="text-left indent-1 text-xl font-semibold tracking-tighter mt-5 ">
          Chat GPT가 추천해준 나의 플레이리스트
        </h1>
        <Link to="/RecPlayList">
          <button className="flex text-custom-lightgray mt-6 mr-2 text-sm">더보기</button>
        </Link>
      </div>
      {recMusic !== null ? (
        <Swiper direction={"vertical"} slidesPerView={4} className="h-[30%]">
          {MusicDummy.map((item) => (
            <SwiperSlide key={item.id}>
              <SingleMusic key={item.id} item={item} />
            </SwiperSlide>
          ))}
        </Swiper>
      ) : (
        <BeforeRegistration />
      )}

      {/* ChatGPT가 추천해준 남의 플레이리스트 */}
      <div>
        <h1 className="text-left indent-1 text-xl font-semibold tracking-tighter mt-8">
          ChatGpt가 추천해준 남의 플레이리스트
        </h1>
        <Link to="RecPlayList">
          <SavedPlaylist />
        </Link>
      </div>

      {/* 최근에 재생한 목록 */}
      <h1 className="text-left indent-1 text-xl font-semibold tracking-tighter mt-8 mb-2">최근에 재생한 목록</h1>
      <Swiper direction={"vertical"} slidesPerView={1} className="h-[9%]">
        {recentlyPlayed.slice(0, 20).map((song) => (
          <SwiperSlide key={song.id}>
            <SingleMusic key={song.id} item={song} />
          </SwiperSlide>
        ))}
      </Swiper>
      {/* <MiniPLayer /> */}
    </div>
  );
};

export default Main;
