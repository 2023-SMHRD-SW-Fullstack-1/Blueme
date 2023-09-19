/*
작성자: 이지희
날짜(수정포함): 2023-09-16
설명: 사용자 라이브러리
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-19
설명: 사용자 라이브러리 웹 디자인 및 반응형
*/

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import LikedList from "../../components/Library/LikedList";
import SavedPlaylist from "../../components/Library/SavedPlaylist";
import SingleRecPlayList from "../rec/SingleRecPlayList";
import { Swiper, SwiperSlide } from "swiper/react";
import { useSelector } from "react-redux/es/hooks/useSelector";

const Library = () => {
  const [myRecMusicList, setMyRecMusicList] = useState([]);
  const user = useSelector((state) => state.memberReducer.user);
  const id = user.id;

  useEffect(() => {
    axios
      .get(`http://172.30.1.27:8104/recMusiclist/recent/${id}`) //나의 추천 플리 불러오기
      .then((res) => {
        setMyRecMusicList(res.data); //나의 플레이리스트에 저장
        console.log("myplaylist", res);
      })
      .catch((err) => console.log(err));
  }, [id]);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-2 h-full font-semibold tracking-tighter">
      <br />
      <div className="flex flex-col md:flex-row">
        <div className="w-full md:w-1/2 pr-0 md:pr-2 order-last md:order-first lg:pr-10  ">
          <p className="text-left text-xl ml-[5px] pt-[20px] lg:pt-[100px] lg:text-3xl">저장한 플레이리스트</p>
          <SavedPlaylist />

          {/* ChatGPT가 추천해준 나의 플레이리스트 */}
          <h1 className="overflow-hidden text-left indent-1 text-xl font-semibold tracking-tighter mt-10 hidden md:block lg:text-3xl">
            Chat GPT가 추천해준 나의 플레이리스트
          </h1>

          {myRecMusicList.length !== 0 ? (
            <Swiper direction={"vertical"} slidesPerView={4} className="h-[33%] hidden md:block">
              {myRecMusicList &&
                myRecMusicList.recMusiclistDetails.map((item) => (
                  <SwiperSlide key={item.recMusiclistDetailId}>
                    <SingleRecPlayList key={item.musicId} item={item} />
                  </SwiperSlide>
                ))}
            </Swiper>
          ) : null}
        </div>

        <div className="w-full md:w-1/2 pl-0 md:pl-2 mt-[30px] md:mt-[10px] order-first md:order-last  lg:pr-10">
          <div className="flex mt-14 xs:mt-5 mb-3">
            <span className="text-left text-xl ml-[5px]  lg:pt-[70px] lg:text-3xl lg:mb-5 ">좋아요 누른 음악 목록</span>
            <button className="ml-auto text-custom-gray mr-2 text-sm">
              <Link to="/LikedPlaylist">더보기</Link>
            </button>
          </div>
          <LikedList />
        </div>
      </div>
    </div>
  );
};

export default Library;
