/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 플레이 리스트
*/
/*
작성자: 신지훈
날짜(수정포함): 2023-09-14
설명: 추천 음악 플레이 리스트
*/
import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import MusicDummy from "../../dummy/MusicDummy.json";
import SingleMusic from "../../components/Library/SingleMusic";
import { Link } from "react-router-dom";
import axios from "axios";
import SingleRecPlayList from "./SingleRecPlayList";

const RecPlayList = () => {
  const id = localStorage.getItem("id");
  const [musiclist, setMusiclist] = useState({ recMusiclistDetails: [] }); //추천 받은 리스트

  useEffect(() => {
    const recPlayList = async () => {
      await axios
        .get(`http://172.30.1.27:8104/recMusiclist/recent/19`)
        .then((res) => {
          console.log(res);
          setMusiclist(res.data);
          // localStorage.setItem('recMusiclist', res.data[0].img, res.data[0].reason)
        })
        .catch((err) => console.log(err));
    };
    recPlayList();
  }, []);

  return (
    // 추천 받은 음악 리스트
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-[1750px] text-custom-white p-3 ">
      <br />
      <br />
      <h1 className="text-center text-xl mt-[10px] font-semibold tracking-tight p-7 overflow-scroll h-[120px] mb-10">
        {musiclist.reason}
      </h1>

      {/* 전체 재생/ 전체 저장 버튼 */}
      <div className="flex justify-between mb-6">
        <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">
          전체 재생
        </button>
        <Link to="/PlaylistRename">
          <button className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl ">
            전체 저장
          </button>
        </Link>
      </div>

      {/* 추천 받은 음악 리스트 목록 */}
      {/* <Swiper direction={'vertical'} slidesPerView={8.2} className="h-[65%]"> */}
      {musiclist &&
        musiclist.recMusiclistDetails.map((item) => (
          <div key={item.recMusiclistDetailId}>
            <SingleRecPlayList key={item.id} item={item} />
          </div>
        ))}
      {/* </Swiper> */}
    </div>
  );
};

export default RecPlayList;
