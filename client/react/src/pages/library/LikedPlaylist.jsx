/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: MusicIds 수정
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-20
설명: 디자인 수정
*/

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";

// import Component
import SingleMusic from "../../components/Library/SingleMusic";

const LikedPlaylist = () => {
  // 사용자 user_id
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  const [ids, setIds] = useState([]);
  const [likedMusics, setLikedMusics] = useState([]);

  const dispatch = useDispatch();

  useEffect(() => {
    const fetchLikedList = async () => {
      try {
        const response = await axios.get(`/likemusics/${userId}`);
        console.log("더보기 response : ", response.data);
        setLikedMusics(response.data);
        setIds(response.data.map((music) => music.musicId));
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
    <div
      className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700  text-custom-white h-full pt-20"
      onClick={handleListClick}
    >
      {/* 플레이리스트 */}
      <div className="flex flex-col items-center justify-center">
        <p className="text-3xl py-5">내가 좋아요 누른 곡</p>
      </div>
      <div className="flex justify-between ml-4 mr-4 py-2">
        <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">랜덤 재생</button>
        <Link>
          {/* <button className="bg-custom-darkgray text-custom-lightpurple w-40 h-8">
            전체 저장
          </button> */}
        </Link>
      </div>
      {/* 음악 데이터 */}
      <div className="h-[70%] ml-3 mr-3 overflow-y-scroll hide-scrollbar">
        {likedMusics.map((song) => (
          <SingleMusic key={song.id} item={song} />
        ))}
      </div>
    </div>
  );
};

export default LikedPlaylist;
